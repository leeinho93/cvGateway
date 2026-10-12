package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.RiskScorePeriod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code RiskScores}.
 *
 * <p><b>리스크 스코어 (Risk Scores)</b>
 * 리스크 스코어 산출에 쓰이는 액티비티 태그 집계 기간 설정.
 *
 * <p>오퍼레이션 2개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionRiskScores",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface RiskScoresClient {

    /**
     * Get Activity Tags Period for Risk Scores
     *
     * <p>Get the activity tags period used to compute risk scores.
     *
     * <p>리스크 스코어 산출에 쓰이는 액티비티 태그 집계 기간을 조회한다.
     *
     * <p>{@code GET /risk-score/activity-tags-period}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 집계 기간 (초)
     */
    @GetMapping("/risk-score/activity-tags-period")
    RiskScorePeriod getRiskScorePeriod();

    /**
     * Set Activity Tags Period for Risk Scores
     *
     * <p>Set the activity tags period used to compute risk scores.
     *
     * <p>리스크 스코어 산출에 쓰이는 액티비티 태그 집계 기간을 설정한다.
     *
     * <p>{@code PUT /risk-score/activity-tags-period}
     *
     * <p>{@code 204} - success. Config has been updated successfully
     * <p>{@code 400} - bad request. Not a valid json or an error occurred
     * <p>{@code 401} - incorrect token
     *
     * @param payload payload (페이로드)
     */
    @PutMapping("/risk-score/activity-tags-period")
    void putRiskScorePeriod(@RequestBody RiskScorePeriod payload);
}
