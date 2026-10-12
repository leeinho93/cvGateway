package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetRiskScore}.
 *
 * <p>RiskScore json representation when fetched in a Preset
 *
 * <p>프리셋의 리스크 스코어 정보.
 */
@Data
public class PresetRiskScore {
    /** {@code id} */
    private String id;

    /** {@code max} */
    private Long max;

    /** {@code min} */
    private Long min;
}
