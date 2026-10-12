package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SEAProxyConfig}.
 *
 * <p>SEA 프록시 설정.
 */
@Data
public class SEAProxyConfig {
    /** {@code password} */
    private String password;

    /** {@code port} */
    private Long port;

    /** {@code scheme} */
    private String scheme;

    /** {@code server} */
    private String server;

    /** {@code username} */
    private String username;
}
