package com.cvGateway.cvGateway.cybervision.config;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 현재 사용 중인 Cyber Vision 접속 정보를 메모리에 들고 있는 보관소.
 *
 * <p>접속 정보는 설정 파일이 아니라 <b>외부 시스템이 런타임에 주입</b>한다
 * ({@code POST /api/cyber-vision/connection}). DB 에도 남기지만 그건 재기동 복구용이고,
 * 실제 호출 경로가 읽는 것은 여기다.
 *
 * <p>DB 에는 {@code id} 별로 여러 행이 있을 수 있지만, 게이트웨이가 동시에 바라보는 Center 는
 * <b>하나</b>다. 여기 담긴 것이 그 하나이며, 새 정보가 주입되면 통째로 교체된다.
 *
 * <p>스레드 안전하다. 주소와 토큰을 한 객체에 담아 한 번에 바꾸므로, 호출 중에 둘이 섞인 상태는
 * 보이지 않는다 — 예전 주소로 새 토큰을 보내는 일이 없다.
 */
@Slf4j
@Component
public class CyberVisionConnectionStore {

    private final AtomicReference<CyberVisionConnection> current = new AtomicReference<>();

    /** 접속 정보를 넣거나 교체한다. */
    public void update(CyberVisionConnection connection) {
        boolean replaced = current.get() != null;
        current.set(connection);
        // 토큰 값은 절대 로그에 남기지 않는다. 주소와 식별자는 추적에 필요하므로 남긴다.
        log.info("Cyber Vision 접속 정보를 {} 했다 (id={}, url={}, switch={}, version={})",
                replaced ? "교체" : "등록",
                connection.id(), connection.centerUrl(), connection.switchName(), connection.version());
    }

    /** 보관된 정보를 지운다. */
    public void clear() {
        CyberVisionConnection removed = current.getAndSet(null);
        if (removed != null) {
            log.info("Cyber Vision 접속 정보를 제거했다 (id={})", removed.id());
        }
    }

    /** 현재 접속 정보. 아직 주입되지 않았으면 비어 있다. */
    public Optional<CyberVisionConnection> get() {
        return Optional.ofNullable(current.get());
    }

    /** API 토큰. */
    public Optional<String> token() {
        return get().map(CyberVisionConnection::token);
    }

    /** Center 베이스 URL. */
    public Optional<String> url() {
        return get().map(CyberVisionConnection::centerUrl);
    }

    /** 주소와 토큰이 모두 들어와 있는지. 호출 전에 확인한다. */
    public boolean isConfigured() {
        return current.get() != null;
    }
}
