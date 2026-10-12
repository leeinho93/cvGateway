package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorCaptureModeFilter}.
 *
 * <p>센서 캡처 모드 필터. all, industrial_only, optimal 또는 커스텀 모드.
 */
@Data
public class SensorCaptureModeFilter {
    /** {@code filter} */
    private String filter;
}
