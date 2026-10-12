package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ExtraField}.
 *
 * <p>ExtraField json representation
 *
 * <p>사용자 정의 속성 (키-값).
 */
@Data
public class ExtraField {
    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code value} */
    private String value;
}
