package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PktEntries}.
 *
 * <p>An object containing arrays of packet reaching DPI process
 *
 * <p>패킷 통계 항목.
 */
@Data
public class PktEntries {
    /** An array containing the count of dropped packet by DPI process */
    private List<Double> dropped;

    /** An array containing the count of processed packet by DPI process */
    private List<Double> processed;

    /** An array of timestamps corresponding to the packet data points, measured in milliseconds */
    private List<Long> timestamp;
}
