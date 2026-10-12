package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActiveDiscoveryScanConsolidatedReport}.
 *
 * <p>능동 탐색 스캔 결과 통합 리포트.
 */
@Data
public class ActiveDiscoveryScanConsolidatedReport {
    /** {@code endDate} */
    private Long endDate;

    /** {@code scanID} */
    private String scanID;

    /** {@code scanRequests} */
    private List<ActiveDiscoveryScanConsolidatedReportDetails> scanRequests;

    /** {@code startDate} */
    private Long startDate;

    /** {@code status} */
    private String status;

    /** {@code statusText} */
    private String statusText;
}
