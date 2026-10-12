package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code DetailedDeploymentJWTParams}.
 *
 * <p>상세 배포 JWT 발급 요청 파라미터.
 */
@Data
public class DetailedDeploymentJWTParams {
    /** {@code activeDiscoveryInterfaces} */
    private List<ActiveDiscoveryInterface> activeDiscoveryInterfaces;

    /** {@code captureMode} */
    private String captureMode;

    /** {@code customCaptureMode} */
    private String customCaptureMode;

    /** {@code deploymentName} */
    private String deploymentName;

    /** {@code formatForIOX} */
    private Boolean formatForIOX;

    /** {@code seaEnabled} */
    private Boolean seaEnabled;

    /** {@code seaProxyConfig} */
    private SEAProxyConfig seaProxyConfig;

    /** {@code sensorSerial} */
    private String sensorSerial;
}
