package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code DeploymentJWTParams}.
 *
 * <p>배포 JWT 발급 요청 파라미터.
 */
@Data
public class DeploymentJWTParams {
    /** {@code deploymentName} */
    private String deploymentName;

    /** {@code sensorSerials} */
    private List<String> sensorSerials;
}
