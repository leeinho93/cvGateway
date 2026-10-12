package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code SensorFolder}.
 *
 * <p>센서를 담는 폴더.
 */
@Data
public class SensorFolder {
    /** {@code description} */
    private String description;

    /** {@code healthStatus} */
    private AggregatedStatus healthStatus;

    /** {@code healthStatusList} */
    private List<AggregatedStatus> healthStatusList;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code location} */
    private String location;

    /** {@code maxVersion} */
    private String maxVersion;

    /** {@code minVersion} */
    private String minVersion;

    /** {@code processingStatus} */
    private AggregatedStatus processingStatus;

    /** {@code processingStatusList} */
    private List<AggregatedStatus> processingStatusList;

    /** todo : properties folder */
    private List<String> properties;

    /** {@code sensorCount} */
    private Long sensorCount;

    /** {@code updateStatus} */
    private UpdateStatus updateStatus;
}
