package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Sensor}.
 *
 * <p>Sensor json representation when fetched in a Preset
 *
 * <p>센서. 트래픽을 수집하는 장치.
 */
@Data
public class Sensor {
    /** {@code captureMode} */
    private String captureMode;

    /** {@code centerId} */
    private String centerId;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private String ip;

    /** {@code label} */
    private String label;

    /** {@code serialNumber} */
    private String serialNumber;
}
