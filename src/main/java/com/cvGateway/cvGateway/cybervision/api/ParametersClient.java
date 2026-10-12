package com.cvGateway.cvGateway.cybervision.api;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Parameters}.
 *
 * <p><b>파라미터 (Parameters)</b>
 * Center 에 키-값 형태로 임의의 설정값을 저장하고 조회하는 범용 저장소.
 *
 * <p>오퍼레이션 5개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionParameters",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ParametersClient {

    /**
     * List Parameters Matching a Pattern
     *
     * <p>List of one or many parameter(s) according to a pattern.
     *
     * <p>패턴에 일치하는 파라미터를 조회한다.
     *
     * <p>{@code GET /parameters}
     *
     * <p>{@code 400} - bad request. Should have a pattern
     * <p>{@code 401} - incorrect token
     *
     * @param pattern pattern that will be used for research (조회에 사용할 패턴)
     * @return 키-값 목록
     */
    @GetMapping("/parameters")
    Map<String, Object> getParametersByPattern(@RequestParam(name = "pattern", required = true) String pattern);

    /**
     * Add One or Many Parameters
     *
     * <p>This endpoint is a mean of storing and retrieving parameters in the database in the form of keys and
     * values.
     *
     * <p>파라미터를 하나 이상 추가한다.
     *
     * <p>Center DB 에 키-값 형태로 임의의 값을 저장하고 조회하는 범용 저장소다.
     *
     * <p>{@code POST /parameters}
     *
     * <p>{@code 400} - bad request. Not a valid json or an error occurred
     * <p>{@code 401} - incorrect token
     * <p>{@code 409} - conflict with an existing parameter
     * <p>{@code 412} - precondition failed. Too much depth of parameters or missing values for parameters
     *
     * @param parameters parameters object (파라미터 객체)
     * @return 저장된 키-값 목록
     */
    @PostMapping("/parameters")
    Map<String, Object> postParameter(@RequestBody Map<String, Object> parameters);

    /**
     * Update a Parameter
     *
     * <p>This endpoint is a mean of updating and retrieving parameters in the database in the form of keys
     * and values.
     *
     * <p>파라미터를 수정한다.
     *
     * <p>{@code PUT /parameters}
     *
     * <p>{@code 400} - bad request. Not a valid json or an error occurred
     * <p>{@code 401} - incorrect token
     * <p>{@code 412} - precondition failed. Too much depth of parameters or missing values for parameters
     *
     * @param parameters parameters object (파라미터 객체)
     * @return 수정된 키-값 목록
     */
    @PutMapping("/parameters")
    Map<String, Object> putParameter(@RequestBody Map<String, Object> parameters);

    /**
     * List Parameters Matching a Key
     *
     * <p>List of one or many parameter(s) according to a key.
     *
     * <p>키에 해당하는 파라미터를 조회한다.
     *
     * <p>{@code GET /parameters/{key}}
     *
     * <p>{@code 400} - bad request. Should have a key
     * <p>{@code 401} - incorrect token
     *
     * @param key key that will be used for research (조회에 사용할 키)
     * @return 키-값 목록
     */
    @GetMapping("/parameters/{key}")
    Map<String, Object> getParametersByKey(@PathVariable("key") String key);

    /**
     * Delete a Parameter
     *
     * <p>파라미터를 삭제한다.
     *
     * <p>{@code DELETE /parameters/{key}}
     *
     * <p>{@code 204} - no content. Deletion successful
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - parameter not found
     *
     * @param key key to delete (삭제할 키)
     */
    @DeleteMapping("/parameters/{key}")
    void deleteParameter(@PathVariable("key") String key);
}
