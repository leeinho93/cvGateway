package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code DiskIOEntries}.
 *
 * <p>An object containing arrays of disk I/O data points
 *
 * <p>센서 디스크 I/O 통계 항목.
 */
@Data
public class DiskIOEntries {
    /** An array of disk I/O read data points, measured in B/s */
    private List<Double> read;

    /** An array of timestamps corresponding to the disk I/O data points, measured in milliseconds */
    private List<Long> timestamp;

    /** An array of disk I/O write data points, measured in B/s */
    private List<Double> write;
}
