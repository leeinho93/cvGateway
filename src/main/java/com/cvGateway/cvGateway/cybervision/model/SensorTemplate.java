package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * {@code SensorTemplate}.
 *
 * <p>센서 템플릿. 여러 센서에 동일 설정을 적용한다.
 */
@Data
public class SensorTemplate {
    /** {@code config} */
    private Map<String, Object> config;

    /** {@code description} */
    private String description;

    /** {@code editable} */
    private Boolean editable;

    /** {@code id} */
    private String id;

    /** {@code lastUpdate} */
    private Long lastUpdate;

    /** {@code name} */
    private String name;

    /** {@code sensorCount} */
    private Long sensorCount;

    /** {@code sensorIds} */
    private List<String> sensorIds;

    /** {@code sensorsStatus} */
    private SensorsTemplateStatus sensorsStatus;
}
