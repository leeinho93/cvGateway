package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Difference}.
 *
 * <p>베이스라인 차이 단건.
 */
@Data
public class Difference {
    /** {@code detection} */
    private Long detection;

    /** {@code id} */
    private List<Integer> id;

    /** {@code key} */
    private String key;

    /** {@code oldValue} */
    private String oldValue;

    /** {@code type} */
    private String type;

    /** {@code value} */
    private String value;
}
