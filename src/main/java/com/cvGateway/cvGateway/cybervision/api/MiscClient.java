package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.Version;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Misc}.
 *
 * <p><b>기타 (Misc)</b>
 * 어느 분류에도 속하지 않는 오퍼레이션.
 *
 * <p>오퍼레이션 1개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionMisc",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface MiscClient {

    /**
     * Get Center Version
     *
     * <p>Center 버전 정보를 조회한다.
     *
     * <p>{@code GET /version}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 버전 정보
     */
    @GetMapping("/version")
    Version getVersion();
}
