package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorExplorerEntry}.
 *
 * <p>센서 탐색기의 항목 (센서 또는 폴더).
 */
@Data
public class SensorExplorerEntry {
    /** {@code object} */
    private Object object;

    /** {@code type} */
    private String type;
}
