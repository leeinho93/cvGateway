package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code NetworkGraphEntries}.
 *
 * <p>네트워크 그래프 항목 묶음.
 */
@Data
public class NetworkGraphEntries {
    /** {@code bandwidth} */
    private NetworkBandwidthEntries bandwidth;

    /** Indicates whether this interface is the collection interface (true) or not (false) */
    @JsonProperty("is_collect_interface")
    private Boolean isCollectInterface;

    /** A label associated with the network interface */
    private String label;

    /** The name of the network interface */
    private String name;

    /** The timestamp of the last data point of the network statistics, measured in milliseconds */
    private Long timestamp;
}
