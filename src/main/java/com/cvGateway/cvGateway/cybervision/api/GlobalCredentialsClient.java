package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.GlobalCredentials;
import com.cvGateway.cvGateway.cybervision.model.GlobalCredentialsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code GlobalCredentials}.
 *
 * <p><b>전역 자격증명 (Global Credentials)</b>
 * 모든 센서에 공통 적용되는 로그인 계정과 비밀번호.
 *
 * <p>오퍼레이션 4개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionGlobalCredentials",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface GlobalCredentialsClient {

    /**
     * Get Global Credentials
     *
     * <p>전역 자격증명을 조회한다.
     *
     * <p>{@code GET /globalCredentials}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - global credentials not found
     *
     * @return 전역 자격증명
     */
    @GetMapping("/globalCredentials")
    GlobalCredentials getGlobalCredentials();

    /**
     * Create Global Credentials
     *
     * <p>Add one or many parameters. This endpoint is a mean of storing and retrieving parameters in the
     * database in the form of keys and values.
     *
     * <p>전역 자격증명을 등록한다.
     *
     * <p>키-값 형태로 값을 저장하고 조회하는 방식이다.
     *
     * <p>{@code POST /globalCredentials}
     *
     * <p>{@code 400} - bad request. Not a valid json or an error occurred
     * <p>{@code 401} - incorrect token
     * <p>{@code 409} - conflict with existing global credentials
     * <p>{@code 412} - precondition failed. Too much depth of parameters or missing values for global credentials
     *
     * @param parameters SensorsGlobalCredentials object (SensorsGlobalCredentials 객체)
     * @return 등록된 전역 자격증명
     */
    @PostMapping("/globalCredentials")
    GlobalCredentials postGlobalCredentials(@RequestBody GlobalCredentialsRequest parameters);

    /**
     * Update Global Credentials
     *
     * <p>전역 자격증명을 수정한다.
     *
     * <p>{@code PUT /globalCredentials}
     *
     * <p>{@code 400} - bad request. Not a valid json or an error occurred
     * <p>{@code 401} - incorrect token
     * <p>{@code 412} - precondition failed. Too much depth of parameters or missing values for global credentials
     *
     * @param parameters SensorsGlobalCredentials object (SensorsGlobalCredentials 객체)
     * @return 수정된 전역 자격증명
     */
    @PutMapping("/globalCredentials")
    GlobalCredentials putGlobalCredentials(@RequestBody GlobalCredentialsRequest parameters);

    /**
     * Delete Global Credentials
     *
     * <p>전역 자격증명을 삭제한다.
     *
     * <p>{@code DELETE /globalCredentials}
     *
     * <p>{@code 204} - no content. Deletion successful
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - parameter not found
     */
    @DeleteMapping("/globalCredentials")
    void deleteGlobalCredentials();
}
