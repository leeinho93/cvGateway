package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ActiveDiscoveryProfilePreset}.
 *
 * <p>프로필에 연결된 프리셋 참조 (id, label).
 */
@Data
public class ActiveDiscoveryProfilePreset {
    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;
}
