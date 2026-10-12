package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActiveDiscoveryPolicy}.
 *
 * <p>능동 탐색 정책. 어떤 프로토콜로 어떻게 질의할지 정의한다.
 */
@Data
public class ActiveDiscoveryPolicy {
    /** {@code broadcast} */
    private List<ActiveDiscoveryProtocol> broadcast;

    /** {@code id} */
    private String id;

    /** {@code name} */
    private String name;

    /** {@code relatedProfiles} */
    private List<ActiveDiscoveryProfileShort> relatedProfiles;

    /** {@code unicast} */
    private List<ActiveDiscoveryProtocol> unicast;
}
