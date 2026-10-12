package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code RiskScoreDevicesCountResponse}.
 *
 * <p>리스크 스코어별 디바이스 수 조회 응답.
 */
@Data
public class RiskScoreDevicesCountResponse {
    /** {@code centers} */
    private List<RiskScoreDevicesCount> centers;

    /** {@code total} */
    private RiskScoreDevicesCount total;
}
