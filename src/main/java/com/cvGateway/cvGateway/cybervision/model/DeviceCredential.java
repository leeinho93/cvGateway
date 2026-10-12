package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeviceCredential}.
 *
 * <p>디바이스에서 수집된 자격증명 정보.
 */
@Data
public class DeviceCredential {
    /** {@code algo} */
    private String algo;

    /** {@code creationTime} */
    private Long creationTime;

    /** {@code deviceId} */
    private String deviceId;

    /** {@code lastUpdate} */
    private Long lastUpdate;

    /** {@code password} */
    private String password;

    /** {@code protocol} */
    private String protocol;

    /** {@code username} */
    private String username;
}
