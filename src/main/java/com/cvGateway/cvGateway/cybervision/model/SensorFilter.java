package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code SensorFilter}.
 *
 * <p>센서 조회 필터.
 */
@Data
public class SensorFilter {
    /** The capture mode of the sensor, which may be all, optimal, industrial only or custom */
    @JsonProperty("capture_mode")
    private String captureMode;

    /** If capture_mode is "custom", this contains the value of the custom filter */
    @JsonProperty("custom_input")
    private String customInput;
}
