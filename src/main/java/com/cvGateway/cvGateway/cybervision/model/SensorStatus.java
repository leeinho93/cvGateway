package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorStatus}.
 *
 * <p>센서 상태.
 */
@Data
public class SensorStatus {
    /** {@code enrollmentStatus} */
    private String enrollmentStatus;

    /** {@code lastReceivedSysinfo} */
    private Long lastReceivedSysinfo;

    /** {@code operationalStatus} */
    private String operationalStatus;
}
