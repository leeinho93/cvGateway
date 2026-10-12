package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code VariableCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>변수 개수 집계 결과.
 */
@Data
public class VariableCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
