package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetDeviceFilterPayload}.
 *
 * <p>contains devices filters we want for the preset target by one request
 *
 * <p>프리셋 디바이스 필터 설정 페이로드.
 */
@Data
public class PresetDeviceFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
