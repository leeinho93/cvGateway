package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetComponentFilterPayload}.
 *
 * <p>contains components filters we want for the preset target by one request
 *
 * <p>프리셋 컴포넌트 필터 설정 페이로드.
 */
@Data
public class PresetComponentFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
