package com.cvGateway.cvGateway.cybervision.config;

import org.springframework.context.annotation.Bean;

import feign.Client;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.hc5.ApacheHttp5Client;

/**
 * Cyber Vision Feign 클라이언트 공통 설정.
 *
 * <p>생성된 26개 클라이언트가 모두 {@code configuration = CyberVisionFeignConfig.class} 로 이 설정을 참조한다.
 *
 * <p>{@code @Configuration} 을 일부러 붙이지 않았다. 붙이면 부모 컨텍스트에 올라가
 * Cyber Vision 과 무관한 다른 Feign 클라이언트에까지 인터셉터가 적용된다.
 *
 * <p>같은 이유로 {@code @EnableConfigurationProperties} 도 여기 두지 않는다. 이 클래스는 Feign
 * 자식 컨텍스트에서 읽히므로 설정 빈이 그 안에만 만들어져, 메인 컨텍스트의 서비스들이 찾지 못한다.
 * {@link CyberVisionConfig} 에서 등록하고 여기서는 주입만 받는다.
 */
public class CyberVisionFeignConfig {

    /**
     * 요청 직전에 Center 주소를 현재 설정값으로 갈아끼우는 클라이언트.
     *
     * <p>주소가 DB 에서 오는 런타임 값이라 {@code @FeignClient(url = ...)} 의 고정 주소로는
     * 대응할 수 없다. 자세한 사정은 {@link DynamicUrlClient} 에 적어 두었다.
     *
     * <p>PATCH 를 지원하려면 Apache HttpClient 5 가 필요하다. Feign 기본 클라이언트는
     * {@code HttpURLConnection} 기반이라 PATCH 를 보내지 못한다.
     */
    @Bean
    public Client cyberVisionClient(CyberVisionConnectionStore connectionStore) {
        return new DynamicUrlClient(new ApacheHttp5Client(), connectionStore);
    }

    /**
     * 모든 요청에 인증 토큰과 공통 헤더를 붙인다.
     *
     * <p>스펙의 {@code securityDefinitions.token} 은 {@code x-token-id} 헤더를 요구한다.
     * 토큰 값은 설정이 아니라 {@link CyberVisionConnectionStore} 에서 가져온다 — 외부 시스템이
     * 런타임에 주입하는 값이라 매 호출 시점에 읽어야 교체가 즉시 반영된다.
     *
     * <p>토큰이 없으면 빈 헤더로 호출해 401 을 받는 대신 즉시 실패시킨다. 나가지 않을 요청으로
     * Center 를 두드릴 이유가 없고, 원인도 더 분명하게 드러난다.
     */
    @Bean
    public RequestInterceptor cyberVisionRequestInterceptor(
            CyberVisionProperties properties, CyberVisionConnectionStore connectionStore) {

        return template -> {
            template.header("x-token-id",
                    connectionStore.token().orElseThrow(CyberVisionNotConfiguredException::new));
            template.header("Accept-Language", properties.getAcceptLanguage());
        };
    }

    /** 에러 응답에 본문을 실어 던지도록 교체한다. */
    @Bean
    public ErrorDecoder cyberVisionErrorDecoder() {
        return new CyberVisionErrorDecoder();
    }

    /**
     * 요청/응답 로깅 수준.
     *
     * <p>{@code BASIC} 은 메서드/URL/상태/소요시간만 남긴다. 실제 로그 출력은
     * {@code logging.level.com.cvGateway.cvGateway.cybervision.api} 를 {@code DEBUG} 로 올려야 보인다.
     */
    @Bean
    public Logger.Level cyberVisionLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
