package com.cvGateway.cvGateway.cybervision.model;

import java.time.OffsetDateTime;

import lombok.Data;

/**
 * {@code Center}.
 *
 * <p>Cyber Vision Center. 센서를 관리하는 중앙 서버.
 */
@Data
public class Center {
    /** {@code enrolledAt} */
    private OffsetDateTime enrolledAt;

    /** {@code fingerprint} */
    private String fingerprint;

    /** {@code hasSomeSensors} */
    private Boolean hasSomeSensors;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private String ip;

    /** {@code ipv6} */
    private String ipv6;

    /** {@code label} */
    private String label;

    /** {@code status} */
    private centerStatus status;

    /** {@code syncDelay} */
    private Long syncDelay;

    /** {@code uptimeInSeconds} */
    private Long uptimeInSeconds;

    /** {@code versions} */
    private versions versions;
}
