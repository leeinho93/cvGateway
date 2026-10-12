package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetBadge}.
 *
 * <p>Badge json representation when fetched in a Preset
 *
 * <p>프리셋에 표시되는 배지 정보.
 */
@Data
public class PresetBadge {
    /** {@code description} */
    private String description;

    /** {@code iconKey} */
    private String iconKey;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;
}
