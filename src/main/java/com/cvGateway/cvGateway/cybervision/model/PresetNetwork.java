package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetNetwork}.
 *
 * <p>Network json representation when fetched in a Preset
 *
 * <p>프리셋에 연결된 네트워크.
 */
@Data
public class PresetNetwork {
    /** {@code id} */
    private String id;

    /** {@code subnet} */
    private String subnet;

    /** {@code vlan} */
    private Long vlan;
}
