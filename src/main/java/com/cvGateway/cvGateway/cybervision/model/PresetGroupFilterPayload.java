package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetGroupFilterPayload}.
 *
 * <p>contains groups filters we want for the preset target by one request
 *
 * <p>프리셋 그룹 필터 설정 페이로드.
 */
@Data
public class PresetGroupFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayloadForPresetGroup value;
}
