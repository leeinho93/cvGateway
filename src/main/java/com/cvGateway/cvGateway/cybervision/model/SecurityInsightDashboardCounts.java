package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code SecurityInsightDashboardCounts}.
 *
 * <p>보안 인사이트 대시보드 집계 수치.
 */
@Data
public class SecurityInsightDashboardCounts {
    /** {@code bottom} */
    private List<SecurityInsightDashboardElement> bottom;

    /** {@code top} */
    private List<SecurityInsightDashboardElement> top;
}
