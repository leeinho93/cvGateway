package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Baseline}.
 *
 * <p>베이스라인. 정상으로 간주하는 네트워크 상태 스냅샷.
 */
@Data
public class Baseline {
    /** {@code activityLastDiffTime} */
    private Long activityLastDiffTime;

    /** {@code componentLastDiffTime} */
    private Long componentLastDiffTime;

    /** {@code countDifferences} */
    private CountDifferences countDifferences;

    /** {@code creationPeriodEnd} */
    private Long creationPeriodEnd;

    /** {@code creationPeriodStart} */
    private Long creationPeriodStart;

    /** {@code creationTime} */
    private Long creationTime;

    /** {@code creatorEmail} */
    private String creatorEmail;

    /** {@code description} */
    private String description;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code lastEditTime} */
    private Long lastEditTime;

    /** {@code lastScanTime} */
    private Long lastScanTime;

    /** {@code nextScanTime} */
    private Long nextScanTime;

    /** {@code presetId} */
    private String presetId;

    /** {@code status} */
    private String status;
}
