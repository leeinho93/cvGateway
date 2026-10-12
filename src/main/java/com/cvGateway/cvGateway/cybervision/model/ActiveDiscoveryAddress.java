package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActiveDiscoveryAddress}.
 *
 * <p>능동 탐색이 사용하는 IP 주소와 서브넷 정보.
 */
@Data
public class ActiveDiscoveryAddress {
    /** {@code activeDiscoveryIPs} */
    private List<String> activeDiscoveryIPs;

    /** {@code activeDiscoveryMAC} */
    private String activeDiscoveryMAC;
}
