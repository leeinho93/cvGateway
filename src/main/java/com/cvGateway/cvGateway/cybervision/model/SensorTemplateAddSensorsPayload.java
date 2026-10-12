package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code SensorTemplateAddSensorsPayload}.
 *
 * <p>센서 템플릿에 센서를 추가하는 요청 본문.
 */
@Data
public class SensorTemplateAddSensorsPayload {
    /** {@code sensorIds} */
    private List<String> sensorIds;
}
