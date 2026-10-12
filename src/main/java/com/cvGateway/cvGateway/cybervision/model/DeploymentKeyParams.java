package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeploymentKeyParams}.
 *
 * <p>JSON representation of the params to create a deployment key
 *
 * <p>배포 키 생성/수정 파라미터.
 */
@Data
public class DeploymentKeyParams {
    /** {@code enable} */
    private Boolean enable;

    /** {@code expirationDate} */
    private String expirationDate;

    /** {@code maxUsageCount} */
    private Long maxUsageCount;

    /** {@code name} */
    private String name;
}
