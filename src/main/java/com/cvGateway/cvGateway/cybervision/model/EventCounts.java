package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code EventCounts}.
 *
 * <p>이벤트 개수 묶음.
 */
@Data
public class EventCounts {
    /** {@code created} */
    private EventCountBySeverity created;

    /** {@code total} */
    private EventCountBySeverity total;
}
