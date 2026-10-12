package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeviceCountResult}.
 *
 * <p>디바이스 개수 집계 결과.
 */
@Data
public class DeviceCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
