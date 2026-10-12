package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GroupCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>그룹 개수 집계 결과.
 */
@Data
public class GroupCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
