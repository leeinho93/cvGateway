package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetSensorFilter}.
 *
 * <p>PresetSensorFilter explicit how a Sensor will be used as filter in a Preset
 *
 * <p>프리셋의 센서 필터.
 */
@Data
public class PresetSensorFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private Sensor value;
}
