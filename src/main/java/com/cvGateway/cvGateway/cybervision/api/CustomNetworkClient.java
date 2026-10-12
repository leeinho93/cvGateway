package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;
import java.util.Map;

import com.cvGateway.cvGateway.cybervision.model.CheckCustomNetworkResult;
import com.cvGateway.cvGateway.cybervision.model.CustomNetworkCreate;
import com.cvGateway.cvGateway.cybervision.model.CustomNetworkUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code CustomNetwork}.
 *
 * <p><b>사용자 정의 네트워크 (Custom Networks)</b>
 * IP 대역에 이름과 속성을 부여해 내부망을 분류한다. 생성·수정·삭제가 모두 벌크 단위로 동작한다.
 *
 * <p>오퍼레이션 5개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionCustomNetwork",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface CustomNetworkClient {

    /**
     * Get Custom Networks
     *
     * <p>사용자 정의 네트워크 목록을 조회한다.
     *
     * <p>{@code GET /networks/}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 네트워크 설정 목록
     */
    @GetMapping("/networks/")
    List<Map<String, Object>> getCustomNetworkSettingsList();

    /**
     * Create Custom Networks
     *
     * <p>Bulk create custom network.
     *
     * <p>사용자 정의 네트워크를 일괄 생성한다.
     *
     * <p>{@code POST /networks/}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param networks custom networks settings (사용자 정의 네트워크 설정)
     * @return 생성 결과
     */
    @PostMapping("/networks/")
    String createCustomNetworks(@RequestBody List<CustomNetworkCreate> networks);

    /**
     * Update Custom Networks
     *
     * <p>Bulk update custom network.
     *
     * <p>사용자 정의 네트워크를 일괄 수정한다.
     *
     * <p>{@code PUT /networks/}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - custom network not found
     *
     * @param networks custom networks settings (사용자 정의 네트워크 설정)
     * @return 수정 결과
     */
    @PutMapping("/networks/")
    String updateCustomNetworks(@RequestBody List<CustomNetworkUpdate> networks);

    /**
     * Delete Custom Networks
     *
     * <p>Bulk delete custom network.
     *
     * <p>사용자 정의 네트워크를 일괄 삭제한다.
     *
     * <p>{@code DELETE /networks/}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - custom network not found
     *
     * @param networks custom networks uuid string array (삭제할 사용자 정의 네트워크 UUID 배열)
     * @return 삭제 결과
     */
    @DeleteMapping("/networks/")
    String deleteCustomNetworkSettingsList(@RequestBody List<String> networks);

    /**
     * Check if Custom Networks Are Valid
     *
     * <p>사용자 정의 네트워크 설정이 유효한지 검증한다.
     *
     * <p>실제로 저장하지 않고 검증만 수행한다.
     *
     * <p>{@code POST /networks/check}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param isCreate whether we want to validate a custom network creation or an update (생성 검증인지 수정 검증인지 여부)
     * @param networks custom networks settings (사용자 정의 네트워크 설정)
     * @return 검증 결과
     */
    @PostMapping("/networks/check")
    CheckCustomNetworkResult validateCustomNetworks(
            @RequestParam(name = "isCreate", required = false) Boolean isCreate,
            @RequestBody List<CustomNetworkCreate> networks);
}
