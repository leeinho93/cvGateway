package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code Thresholds}.
 *
 * <p>임계값 설정.
 */
@Data
public class Thresholds {
    /** {@code RegularGraphThreshold} */
    @JsonProperty("RegularGraphThreshold")
    private NbNodesEdges regularGraphThreshold;

    /** {@code ZonesGraphThreshold} */
    @JsonProperty("ZonesGraphThreshold")
    private NbNodesEdges zonesGraphThreshold;

    /** {@code hardLimitActivities} */
    private Long hardLimitActivities;

    /** {@code minimumMapZonesThreshold} */
    private Long minimumMapZonesThreshold;
}
