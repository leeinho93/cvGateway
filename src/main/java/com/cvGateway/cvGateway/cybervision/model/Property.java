package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Property}.
 *
 * <p>속성 (키-값).
 */
@Data
public class Property {
    /** {@code difference} */
    private Difference difference;

    /** {@code key} */
    private String key;

    /** {@code value} */
    private String value;
}
