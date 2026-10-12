package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeviceTypeRiskStruct}.
 *
 * <p>디바이스 유형별 리스크 집계.
 */
@Data
public class DeviceTypeRiskStruct {
    /** {@code device} */
    private LightDevice device;

    /** {@code riskScoreDescription} */
    private String riskScoreDescription;

    /** {@code score} */
    private Long score;
}
