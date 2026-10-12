package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code AggregatedStatus}.
 *
 * <p>집계된 상태 값.
 */
@Data
public class AggregatedStatus {
    /** {@code count} */
    private Long count;

    /** {@code indicator} */
    private String indicator;

    /** {@code label} */
    private String label;
}
