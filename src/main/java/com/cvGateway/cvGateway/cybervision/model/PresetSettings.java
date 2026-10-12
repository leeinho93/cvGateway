package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetSettings}.
 *
 * <p>프리셋 설정.
 */
@Data
public class PresetSettings {
    /** {@code activeBaselineId} */
    private String activeBaselineId;

    /** {@code defaultSeverities} */
    private DefaultSeverities defaultSeverities;

    /** {@code differenceActivityNew} */
    private Boolean differenceActivityNew;

    /** {@code differenceActivityTagNew} */
    private Boolean differenceActivityTagNew;

    /** {@code differenceComponentNew} */
    private Boolean differenceComponentNew;

    /** {@code differenceComponentPropertyModified} */
    private Boolean differenceComponentPropertyModified;

    /** {@code differenceComponentPropertyNew} */
    private Boolean differenceComponentPropertyNew;

    /** {@code differenceComponentTagNew} */
    private Boolean differenceComponentTagNew;

    /** {@code differenceComponentVariableAccessNew} */
    private Boolean differenceComponentVariableAccessNew;

    /** {@code forceScanPeriod} */
    private Boolean forceScanPeriod;

    /** {@code lastEditUpdate} */
    private Long lastEditUpdate;

    /** {@code presetId} */
    private String presetId;

    /** {@code scanPeriod} */
    private Long scanPeriod;

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
