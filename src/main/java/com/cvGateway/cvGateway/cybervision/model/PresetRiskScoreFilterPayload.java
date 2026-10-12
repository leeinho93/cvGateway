package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetRiskScoreFilterPayload}.
 *
 * <p>contains risk scores filters we want for the preset
 *
 * <p>프리셋 리스크 스코어 필터 설정 페이로드.
 */
@Data
public class PresetRiskScoreFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayloadForPresetRiskScore value;
}
