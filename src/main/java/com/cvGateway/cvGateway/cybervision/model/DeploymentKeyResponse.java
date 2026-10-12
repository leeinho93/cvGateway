package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code DeploymentKeyResponse}.
 *
 * <p>배포 키 정보.
 */
@Data
public class DeploymentKeyResponse {
    /** {@code creationDate} */
    private String creationDate;

    /** {@code deploymentTokens} */
    private List<DeploymentToken> deploymentTokens;

    /** {@code enable} */
    private Boolean enable;

    /** {@code expirationDate} */
    private String expirationDate;

    /** {@code id} */
    private String id;

    /** {@code lastDeploymentStatus} */
    private DeploymentStatus lastDeploymentStatus;

    /** {@code maxUsageCount} */
    private Long maxUsageCount;

    /** {@code name} */
    private String name;

    /** {@code usageCount} */
    private Long usageCount;
}
