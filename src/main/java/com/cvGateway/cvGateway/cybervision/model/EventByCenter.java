package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code EventByCenter}.
 *
 * <p>Center 별 이벤트 집계.
 */
@Data
public class EventByCenter {
    /** {@code critical} */
    private Long critical;

    /** {@code high} */
    private Long high;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code low} */
    private Long low;

    /** {@code medium} */
    private Long medium;

    /** {@code total} */
    private Long total;
}
