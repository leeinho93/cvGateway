package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorExplorerMoveTarget}.
 *
 * <p>이동할 센서/폴더 지정. id 와 type(sensor|folder)으로 구성된다.
 */
@Data
public class SensorExplorerMoveTarget {
    /** 예: {@code "8965c2de-cff9-4afa-bffe-a4ae29de57bf"} */
    private String id;

    /** 예: {@code "sensor"} */
    private String type;
}
