package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code NetworkBandwidthEntries}.
 *
 * <p>An object containing arrays of network bandwidth data points
 *
 * <p>네트워크 대역폭 통계 항목.
 */
@Data
public class NetworkBandwidthEntries {
    /** An array of received network bandwidth data points, measured in B/s */
    private List<Double> received;

    /** An array of timestamps corresponding to the network bandwidth data points, measured in milliseconds */
    private List<Long> timestamp;

    /** An array of transmitted network bandwidth data points, measured in B/s */
    private List<Double> transmitted;
}
