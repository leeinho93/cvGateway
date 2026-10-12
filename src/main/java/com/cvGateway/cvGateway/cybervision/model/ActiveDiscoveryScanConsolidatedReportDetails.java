package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActiveDiscoveryScanConsolidatedReportDetails}.
 *
 * <p>스캔 결과 리포트의 상세 항목.
 */
@Data
public class ActiveDiscoveryScanConsolidatedReportDetails {
    /** {@code endDate} */
    private Long endDate;

    /** {@code ipRanges} */
    private List<String> ipRanges;

    /** {@code ipTargets} */
    private List<String> ipTargets;

    /** {@code ipTargetsCount} */
    private Long ipTargetsCount;

    /** {@code percentageProgress} */
    private Long percentageProgress;

    /** {@code protocol} */
    private String protocol;

    /** {@code protocolMode} */
    private String protocolMode;

    /** {@code scanID} */
    private String scanID;

    /** {@code sensorName} */
    private String sensorName;

    /** {@code startDate} */
    private Long startDate;

    /** {@code status} */
    private String status;

    /** {@code statusText} */
    private String statusText;
}
