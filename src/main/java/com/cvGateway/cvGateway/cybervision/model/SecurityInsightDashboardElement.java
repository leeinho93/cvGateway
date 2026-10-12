package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SecurityInsightDashboardElement}.
 *
 * <p>보안 인사이트 대시보드 항목.
 */
@Data
public class SecurityInsightDashboardElement {
    /** {@code componentsCount} */
    private Long componentsCount;

    /** {@code label} */
    private String label;

    /** {@code value} */
    private Long value;
}
