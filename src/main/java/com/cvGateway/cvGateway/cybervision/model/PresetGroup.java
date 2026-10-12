package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PresetGroup}.
 *
 * <p>Group json representation when fetched in a Preset
 *
 * <p>프리셋에 연결된 그룹.
 */
@Data
public class PresetGroup {
    /** {@code children} */
    private List<PresetGroupFilter> children;

    /** {@code color} */
    private Long color;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code parentId} */
    private String parentId;
}
