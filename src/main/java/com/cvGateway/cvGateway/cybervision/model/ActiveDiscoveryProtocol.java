package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ActiveDiscoveryProtocol}.
 *
 * <p>능동 탐색에 사용할 프로토콜 설정.
 */
@Data
public class ActiveDiscoveryProtocol {
    /** {@code enabled} */
    private Boolean enabled;

    /** {@code id} */
    private String id;

    /** {@code parameters} */
    private String parameters;

    /** {@code protocol} */
    private String protocol;
}
