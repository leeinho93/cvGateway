package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DefaultSeverities}.
 *
 * <p>기본 심각도 구간 설정.
 */
@Data
public class DefaultSeverities {
    /** {@code severityAckDifference} */
    private Integer severityAckDifference;

    /** {@code severityDeleteElement} */
    private Integer severityDeleteElement;

    /** {@code severityDifferenceDetected} */
    private Integer severityDifferenceDetected;

    /** {@code severityIgnoreDifference} */
    private Integer severityIgnoreDifference;

    /** {@code severityNackDifference} */
    private Integer severityNackDifference;
}
