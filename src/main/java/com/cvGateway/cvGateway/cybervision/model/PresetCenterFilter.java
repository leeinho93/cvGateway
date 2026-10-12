package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetCenterFilter}.
 *
 * <p>PresetCenterFilter explicit how a Center will be used as filter in a Preset
 *
 * <p>프리셋의 Center 필터.
 */
@Data
public class PresetCenterFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private Center value;
}
