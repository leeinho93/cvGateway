package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetHasExternalCommunicationsFilterPayload}.
 *
 * <p>외부 통신 보유 여부 필터 페이로드.
 */
@Data
public class PresetHasExternalCommunicationsFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
