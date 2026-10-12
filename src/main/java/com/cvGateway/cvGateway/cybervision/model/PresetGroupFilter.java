package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetGroupFilter}.
 *
 * <p>PresetGroupFilter explicit how a Group will be used as filter in a Preset
 *
 * <p>프리셋의 그룹 필터.
 */
@Data
public class PresetGroupFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private PresetGroup value;
}
