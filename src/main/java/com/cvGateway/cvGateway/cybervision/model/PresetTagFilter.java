package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetTagFilter}.
 *
 * <p>PresetTagFilter explicit how a Tag will be used as filter in a Preset
 *
 * <p>프리셋의 태그 필터.
 */
@Data
public class PresetTagFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private PresetTag value;
}
