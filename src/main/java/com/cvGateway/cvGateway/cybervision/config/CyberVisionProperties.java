package com.cvGateway.cvGateway.cybervision.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Cisco Cyber Vision 접속 설정.
 *
 * <p>{@code application.yaml} 의 {@code cyber-vision.*} 를 바인딩한다.
 */
@Data
@ConfigurationProperties(prefix = "cyber-vision")
public class CyberVisionProperties {

    /*
     * Center 주소와 API 토큰은 여기 두지 않는다. 외부 시스템이
     * POST /api/cyber-vision/connection 으로 주입하고 cv_connection 테이블에 보관된다.
     * 운영 중 Center 를 바꿀 때 재배포가 필요 없도록 한 선택이다.
     */

    /**
     * API 베이스 경로. 스펙의 {@code basePath} 기본값은 {@code /api/3.0} 이다.
     */
    private String basePath = "/api/3.0";

    /*
     * API 토큰은 여기 두지 않는다. 외부 시스템이 POST /api/cyber-vision/token 으로 주입하고
     * CyberVisionTokenStore 가 메모리에 보관한다. 설정 파일이나 환경변수에 자격증명을 남기지 않기 위함이다.
     */

    /**
     * 응답 메시지 언어. 스펙상 일부 오퍼레이션이 {@code Accept-Language} 를 받지만,
     * 게이트웨이에서는 매 호출마다 넘기지 않고 이 값으로 일괄 적용한다.
     */
    private String acceptLanguage = "en";

    /**
     * 토큰을 등록할 때 Cyber Vision 에 유효성을 물어볼지 여부.
     *
     * <p>{@code GET /version} 을 한 번 호출해 401 이 오는지로 판정한다. 잘못된 토큰이 DB 에
     * 저장되는 것을 막아 준다. Center 에 닿지 못하는 경우에는 막지 않고 통과시킨다.
     *
     * <p>Center 와 통신할 수 없는 환경에서 토큰만 미리 넣어 두려면 끈다.
     */
    private boolean validateTokenOnUpdate = true;
}
