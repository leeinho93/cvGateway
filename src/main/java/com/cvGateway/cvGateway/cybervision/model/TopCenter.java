package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code TopCenter}.
 *
 * <p>상위 Center 순위 항목.
 */
@Data
public class TopCenter {
    /** {@code centerId} */
    private String centerId;

    /** {@code centerName} */
    private String centerName;

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
