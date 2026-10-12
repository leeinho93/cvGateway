package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code VulnerabilitiesRiskStruct}.
 *
 * <p>취약점 기준 리스크 집계.
 */
@Data
public class VulnerabilitiesRiskStruct {
    /** {@code mostImpactingVulnerability} */
    private VulnerabilityDetail mostImpactingVulnerability;

    /** {@code score} */
    private Long score;
}
