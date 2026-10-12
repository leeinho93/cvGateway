package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code OrphanComponentCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>어느 디바이스에도 속하지 않은 고아 컴포넌트 개수.
 */
@Data
public class OrphanComponentCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
