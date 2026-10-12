package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Credential}.
 *
 * <p>자격증명 (계정·비밀번호).
 */
@Data
public class Credential {
    /** {@code algo} */
    private String algo;

    /** {@code creationTime} */
    private Long creationTime;

    /** {@code lastUpdate} */
    private Long lastUpdate;

    /** {@code password} */
    private String password;

    /** {@code protocol} */
    private String protocol;

    /** {@code username} */
    private String username;
}
