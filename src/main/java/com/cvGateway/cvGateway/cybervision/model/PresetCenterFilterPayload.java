package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetCenterFilterPayload}.
 *
 * <p>contains centers filters we want for the preset target by one request
 *
 * <p>프리셋 Center 필터 설정 페이로드.
 */
@Data
public class PresetCenterFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
