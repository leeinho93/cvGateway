package com.cvGateway.cvGateway.cybervision.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cyber Vision 연동의 메인 컨텍스트 설정.
 *
 * <p>{@link CyberVisionProperties} 를 여기서 등록한다. {@link CyberVisionFeignConfig} 에 두면
 * 안 된다 — 그쪽은 {@code @Configuration} 이 없는 Feign 전용 설정이라 Feign 자식 컨텍스트에만
 * 빈이 만들어지고, 메인 컨텍스트의 서비스들은 그 빈을 찾지 못한다.
 *
 * <p>자식 컨텍스트는 부모의 빈을 볼 수 있으므로 여기 한 번만 등록하면 양쪽에서 모두 쓸 수 있다.
 */
@Configuration
@EnableConfigurationProperties(CyberVisionProperties.class)
public class CyberVisionConfig {
}
