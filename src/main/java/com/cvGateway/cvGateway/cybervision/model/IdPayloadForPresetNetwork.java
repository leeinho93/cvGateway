package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code IdPayloadForPresetNetwork}.
 *
 * <p>프리셋 네트워크 필터용 ID 페이로드.
 */
@Data
public class IdPayloadForPresetNetwork {
    /** {@code id} */
    private String id;

    /** {@code subnet} */
    private String subnet;

    /** {@code vlan} */
    private Long vlan;
}
