package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetTag}.
 *
 * <p>Tag json representation when fetched in a Preset
 *
 * <p>프리셋에 연결된 태그.
 */
@Data
public class PresetTag {
    /** {@code category} */
    private Category category;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code type} */
    private String type;
}
