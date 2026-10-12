package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetTagFilterPayload}.
 *
 * <p>contains tags filters we want for the preset target by one request
 *
 * <p>프리셋 태그 필터 설정 페이로드.
 */
@Data
public class PresetTagFilterPayload {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private IdPayload value;
}
