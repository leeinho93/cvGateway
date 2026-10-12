package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetSensorFilterPayload}.
 *
 * <p>contains sensors filters we want for the preset target by one request
 *
 * <p>프리셋 센서 필터 설정 페이로드.
 */
@Data
public class PresetSensorFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
