package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeploymentStatus}.
 *
 * <p>배포 상태.
 */
@Data
public class DeploymentStatus {
    /** {@code error} */
    private String error;

    /** {@code lastUpdateTime} */
    private Long lastUpdateTime;

    /** {@code success} */
    private Boolean success;
}
