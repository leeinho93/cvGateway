package com.cvGateway.cvGateway.cybervision.connection;

import java.time.Instant;
import java.util.Optional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionProperties;
import com.cvGateway.cvGateway.cybervision.token.CyberVisionTokenValidator;
import com.cvGateway.cvGateway.cybervision.token.InvalidTokenException;
import com.cvGateway.cvGateway.cybervision.token.TokenValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Cyber Vision 접속 정보의 보관과 갱신을 맡는다.
 *
 * <p>접속 정보는 두 곳에 존재한다.
 * <ul>
 *   <li>{@link CyberVisionConnectionStore} — 메모리. 매 API 호출 시 읽는 실사용 경로</li>
 *   <li>{@link CyberVisionConnectionRepository} — DB. 재기동 후 되살리기 위한 보존 경로</li>
 * </ul>
 *
 * <p>쓰기는 항상 <b>DB 먼저, 메모리 나중</b>이다. DB 저장이 실패했는데 메모리만 바뀌면
 * 재기동 시 조용히 예전 값으로 되돌아가 원인을 찾기 어려워진다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CyberVisionConnectionService {

    private final CyberVisionConnectionRepository repository;
    private final CyberVisionConnectionStore connectionStore;
    private final CyberVisionTokenValidator validator;
    private final CyberVisionProperties properties;

    /**
     * 기동이 끝나면 DB 에 보관된 접속 정보를 메모리로 올린다.
     *
     * <p>여러 {@code id} 가 있으면 가장 최근에 주입된 것을 쓴다. 게이트웨이가 동시에 바라보는
     * Center 는 하나이기 때문이다.
     *
     * <p>DB 조회가 실패해도 기동을 막지 않는다. 접속 정보를 못 읽는다고 게이트웨이를 못 띄우면
     * 정보를 주입할 API 조차 열리지 않아 복구 수단이 사라진다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadFromDatabase() {
        try {
            Optional<CyberVisionConnectionEntity> stored = repository.findFirstByOrderByUpdatedAtDesc();

            if (stored.isEmpty()) {
                log.info("DB 에 보관된 Cyber Vision 접속 정보가 없다."
                        + " POST /api/cyber-vision/connection 으로 주입될 때까지 대기한다.");
                return;
            }
            CyberVisionConnectionEntity entity = stored.get();
            connectionStore.update(entity.toConnection());
            log.info("DB 에 보관된 Cyber Vision 접속 정보를 불러왔다 (id={}, 마지막 갱신 {})",
                    entity.getId(), entity.getUpdatedAt());

        } catch (RuntimeException e) {
            log.error("DB 에서 Cyber Vision 접속 정보를 읽지 못했다."
                    + " 접속 정보 없이 기동하며, API 로 주입하면 정상 동작한다.", e);
        }
    }

    /**
     * 접속 정보를 등록하거나 교체한다. DB 와 메모리에 함께 반영된다.
     *
     * <p>같은 {@code id} 로 다시 주입하면 그 행이 교체된다. 다른 {@code id} 면 행이 하나 늘어나고,
     * 게이트웨이는 방금 주입한 것을 현재 설정으로 쓴다.
     *
     * @return 등록 전 검증 결과
     * @throws IllegalArgumentException 필수 값이 비었거나 주소 형식이 아니면
     * @throws InvalidTokenException    Center 가 토큰을 거부하면
     */
    @Transactional
    public TokenValidation update(
            String id, String centerUrl, String switchName, String version, String token) {

        CyberVisionConnection candidate = CyberVisionConnection.of(
                id, centerUrl, switchName, version, token, Instant.now());

        TokenValidation validation = verify(candidate);
        if (validation == TokenValidation.REJECTED) {
            throw new InvalidTokenException();
        }

        CyberVisionConnectionEntity entity = repository.findById(candidate.id())
                .map(found -> {
                    found.applyFrom(candidate);
                    return found;
                })
                .orElseGet(() -> new CyberVisionConnectionEntity(candidate));

        // saveAndFlush 로 지금 바로 DB 에 밀어 넣는다. 더티 체킹에 맡기면 실제 UPDATE 가
        // 트랜잭션 커밋 시점에 나가는데, 그때는 이미 메모리를 갱신한 뒤라 "DB 먼저" 가 성립하지 않는다.
        repository.saveAndFlush(entity);

        connectionStore.update(candidate);
        return validation;
    }

    /**
     * 지정한 {@code id} 의 접속 정보를 지운다.
     *
     * <p>현재 사용 중인 것을 지우면 메모리에서도 함께 비운다. 다른 {@code id} 를 지우는 경우
     * 메모리는 건드리지 않는다 — 지금 쓰고 있는 연결을 끊을 이유가 없다.
     */
    @Transactional
    public void clear(String id) {
        repository.findById(id).ifPresent(repository::delete);

        boolean removedActive = connectionStore.get()
                .map(active -> active.id().equals(id))
                .orElse(false);
        if (removedActive) {
            connectionStore.clear();
        }
    }

    /** 현재 사용 중인 접속 정보. */
    public Optional<CyberVisionConnection> current() {
        return connectionStore.get();
    }

    /**
     * 저장 전에 Cyber Vision 에 접속 정보가 통하는지 물어본다.
     *
     * <p>거부(401)로 확인된 경우에만 등록을 막는다. Center 에 닿지 못한 경우는 통과시킨다 —
     * 토큰이 잘못됐다는 근거가 없는데 막으면, Center 점검 중에 새 정보를 미리 넣어 둘 수 없다.
     * 잘못된 값이었다면 어차피 첫 폴링에서 401 로 드러난다.
     */
    private TokenValidation verify(CyberVisionConnection candidate) {
        if (!properties.isValidateTokenOnUpdate()) {
            return TokenValidation.SKIPPED;
        }
        TokenValidation validation = validator.validate(candidate.centerUrl(), candidate.token());
        if (validation == TokenValidation.UNREACHABLE) {
            log.warn("Cyber Vision 에 확인하지 못한 채 접속 정보를 등록한다."
                    + " 값이 맞는지는 첫 호출에서 드러난다.");
        }
        return validation;
    }
}
