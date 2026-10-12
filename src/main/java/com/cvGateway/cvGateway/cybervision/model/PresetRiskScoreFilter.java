package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetRiskScoreFilter}.
 *
 * <p>PresetRiskScoreFilter explicit how a range between min and max can be used as filter in a Preset
 *
 * <p>프리셋의 리스크 스코어 필터.
 */
@Data
public class PresetRiskScoreFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private PresetRiskScore value;
}
