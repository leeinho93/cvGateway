package com.cvGateway.cvGateway.cybervision.web;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
import com.cvGateway.cvGateway.cybervision.connection.CyberVisionConnectionService;
import com.cvGateway.cvGateway.cybervision.token.TokenValidation;

import lombok.RequiredArgsConstructor;

/**
 * Cyber Vision 접속 정보를 외부 시스템으로부터 주입받는 API.
 *
 * <p>Center 주소와 API 토큰을 설정 파일에 두지 않는다. Cyber Vision 은 토큰 발급 API 를 제공하지
 * 않으므로 관리 UI 에서 사람이 만든 값을 외부 시스템이 이 엔드포인트로 넣어 주고, 게이트웨이는
 * 그 값을 DB 와 메모리에 보관해 이후 모든 호출에 사용한다.
 *
 * <p>DB 에도 남기므로 재기동하면 자동으로 되살아난다. 여러 {@code id} 를 넣을 수 있지만
 * 게이트웨이가 동시에 바라보는 Center 는 하나이며, 가장 최근에 주입된 것을 쓴다.
 *
 * <p><b>주의</b>: 이 엔드포인트는 자격증명을 받는다. 아무나 호출하면 접속 정보를 덮어써
 * 연동을 마비시킬 수 있으므로, 망 분리나 인증으로 접근을 제한해야 한다.
 */
@RestController
@RequestMapping("/api/cyber-vision/connection")
@RequiredArgsConstructor
public class CyberVisionConnectionController {

    private final CyberVisionConnectionService connectionService;

    /**
     * 접속 정보 주입 요청 본문.
     *
     * @param id         외부 시스템이 부여한 식별자. DB 의 키가 된다 (필수)
     * @param centerUrl  Center 베이스 URL. 예: {@code https://192.168.0.10} (필수)
     * @param switchName 대상 스위치 이름 (선택)
     * @param version    버전 문자열 (선택)
     * @param token      Cyber Vision 관리 UI 에서 발급한 API 토큰 (필수)
     */
    public record ConnectionRequest(
            String id, String centerUrl, String switchName, String version, String token) {
    }

    /**
     * 접속 정보 보관 상태.
     *
     * <p>토큰 원문은 담지 않는다. 자격증명을 조회 API 로 되돌려주면 유출 경로가 된다.
     *
     * @param configured 접속 정보가 들어와 있는지
     * @param id         현재 사용 중인 식별자
     * @param centerUrl  현재 사용 중인 Center 주소
     * @param switchName 스위치 이름
     * @param version    버전
     * @param token      앞 8자만 남기고 가린 토큰
     * @param updatedAt  마지막으로 주입된 시각
     * @param validation 등록 시점의 검증 결과. 조회에서는 {@code null}
     */
    public record ConnectionStatus(
            boolean configured,
            String id,
            String centerUrl,
            String switchName,
            String version,
            String token,
            Instant updatedAt,
            TokenValidation validation) {

        static ConnectionStatus of(CyberVisionConnection connection, TokenValidation validation) {
            if (connection == null) {
                return new ConnectionStatus(false, null, null, null, null, null, null, validation);
            }
            return new ConnectionStatus(
                    true,
                    connection.id(),
                    connection.centerUrl(),
                    connection.switchName(),
                    connection.version(),
                    connection.maskedToken(),
                    connection.updatedAt(),
                    validation);
        }
    }

    /**
     * 접속 정보를 등록하거나 교체한다.
     *
     * <p>같은 {@code id} 로 다시 넣으면 그 행이 교체된다. 교체는 즉시 반영되며 재기동이 필요 없다.
     *
     * <p>저장 전에 {@code GET /version} 으로 토큰이 통하는지 확인한다. 401 이 오면 등록을 거부하고,
     * Center 에 닿지 못하면 경고만 남기고 통과시킨다 — 응답의 {@code validation} 으로 구분한다.
     */
    @PostMapping
    public ConnectionStatus update(@RequestBody ConnectionRequest request) {
        TokenValidation validation = connectionService.update(
                request == null ? null : request.id(),
                request == null ? null : request.centerUrl(),
                request == null ? null : request.switchName(),
                request == null ? null : request.version(),
                request == null ? null : request.token());

        return ConnectionStatus.of(connectionService.current().orElse(null), validation);
    }

    /**
     * 현재 접속 정보 보관 상태. 토큰 원문은 나오지 않는다.
     *
     * <p>{@code validation} 은 등록 시점의 판정이라 여기서는 채우지 않는다. 지금 이 순간에도
     * 유효한지 알고 싶다면 아무 조회 API 나 호출해 보는 것이 확실하다.
     */
    @GetMapping
    public ConnectionStatus status() {
        return ConnectionStatus.of(connectionService.current().orElse(null), null);
    }

    /**
     * 지정한 {@code id} 의 접속 정보를 지운다.
     *
     * <p>지금 사용 중인 것을 지우면 새로 주입할 때까지 모든 Cyber Vision 호출이 실패하고
     * 폴링도 멈춘다.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear(@PathVariable String id) {
        connectionService.clear(id);
    }
}
