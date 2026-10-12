package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ActivityCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>액티비티 개수 집계 결과.
 */
@Data
public class ActivityCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
