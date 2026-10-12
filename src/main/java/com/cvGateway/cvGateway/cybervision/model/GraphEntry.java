package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GraphEntry}.
 *
 * <p>그래프 구성 항목 하나.
 */
@Data
public class GraphEntry {
    /** Percentage of CPU, RAM or Disk Usage at the given timestamp */
    private Double data;

    /** The time when the CPU, RAM or Disk Usage data point was recorded, measured in milliseconds */
    private Long timestamp;
}
