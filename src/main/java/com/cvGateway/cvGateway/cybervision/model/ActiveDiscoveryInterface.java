package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ActiveDiscoveryInterface}.
 *
 * <p>능동 탐색 질의를 내보내는 센서의 네트워크 인터페이스.
 */
@Data
public class ActiveDiscoveryInterface {
    /** {@code disableScand} */
    private Boolean disableScand;

    /** {@code iface} */
    private String iface;

    /** {@code ip} */
    private String ip;

    /** {@code netmask} */
    private String netmask;

    /** {@code vlan} */
    private Integer vlan;
}
