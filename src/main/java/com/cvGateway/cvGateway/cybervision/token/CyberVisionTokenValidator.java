package com.cvGateway.cvGateway.cybervision.token;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 등록하려는 접속 정보가 실제로 통하는지 확인한다.
 *
 * <p>Cyber Vision 에는 토큰 검증 전용 API 가 없다. 대신 인증이 필요한 가장 가벼운 엔드포인트인
 * {@code GET /version} 을 한 번 호출해 401 이 오는지로 판정한다. 이 엔드포인트는 파라미터가 없고
 * 응답이 네 필드뿐이며 200 과 401 만 정의돼 있어 판정이 명확하다.
 *
 * <p>Feign 클라이언트를 쓰지 않고 {@link RestClient} 로 직접 호출한다. Feign 은 보관소에 있는
 * 주소와 토큰을 쓰는데, 여기서는 <b>아직 저장하지 않은 후보 값</b>으로 물어봐야 하기 때문이다.
 * 보관소에 미리 넣고 시험하면 검증이 끝나기 전에 폴링이 그 값을 써 버릴 수 있다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CyberVisionTokenValidator {

    private final CyberVisionProperties properties;

    /**
     * 주소와 토큰이 통하는지 물어본다.
     *
     * <p>판정은 <b>명확한 근거가 있을 때만</b> 내린다. 401 이면 확실히 잘못된 토큰이므로
     * {@link TokenValidation#REJECTED}, 연결조차 못 했으면 토큰 탓이라 볼 수 없으므로
     * {@link TokenValidation#UNREACHABLE} 이다.
     *
     * @param baseUrl Center 베이스 URL (뒤 슬래시 없음)
     * @param token   확인할 API 토큰
     */
    public TokenValidation validate(String baseUrl, String token) {
        try {
            RestClient.create(baseUrl + properties.getBasePath())
                    .get()
                    .uri("/version")
                    .header("x-token-id", token)
                    .retrieve()
                    .toBodilessEntity();
            return TokenValidation.VALID;

        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 401) {
                return TokenValidation.REJECTED;
            }
            // 4xx·5xx 중 401 이 아닌 것은 토큰 문제가 아니다. 주소가 틀렸거나 Center 가 앓는 중이다.
            log.warn("접속 정보 검증 중 예상치 못한 응답 (status={}). 토큰 자체는 판단하지 않는다.",
                    e.getStatusCode().value());
            return TokenValidation.UNREACHABLE;

        } catch (RestClientException e) {
            log.warn("검증을 위해 Cyber Vision({}) 에 연결하지 못했다: {}", baseUrl, e.getMessage());
            return TokenValidation.UNREACHABLE;
        }
    }
}
