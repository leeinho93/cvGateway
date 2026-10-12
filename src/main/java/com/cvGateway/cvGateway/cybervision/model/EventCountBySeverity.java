package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code EventCountBySeverity}.
 *
 * <p>심각도별 이벤트 개수.
 */
@Data
public class EventCountBySeverity {
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
