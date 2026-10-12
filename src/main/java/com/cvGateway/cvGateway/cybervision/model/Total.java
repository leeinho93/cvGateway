package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Total}.
 *
 * <p>합계 값.
 */
@Data
public class Total {
    /** {@code critical} */
    private Long critical;

    /** {@code high} */
    private Long high;

    /** {@code low} */
    private Long low;

    /** {@code medium} */
    private Long medium;

    /** {@code total} */
    private Long total;
}
