package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeploymentToken}.
 *
 * <p>배포 토큰.
 */
@Data
public class DeploymentToken {
    /** {@code appName} */
    private String appName;

    /** {@code token} */
    private String token;
}
