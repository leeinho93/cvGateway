package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ComponentCredential}.
 *
 * <p>컴포넌트에서 수집된 자격증명 정보.
 */
@Data
public class ComponentCredential {
    /** {@code algo} */
    private String algo;

    /** {@code componentId} */
    private String componentId;

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
