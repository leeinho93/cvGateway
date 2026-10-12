package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetNetworkFilterPayload}.
 *
 * <p>contains networks filters we want for the preset
 *
 * <p>프리셋 네트워크 필터 설정 페이로드.
 */
@Data
public class PresetNetworkFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayloadForPresetNetwork value;
}
