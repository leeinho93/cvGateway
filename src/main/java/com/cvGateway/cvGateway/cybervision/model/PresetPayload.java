package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetPayload}.
 *
 * <p>PresetPayload is the structure that contains every information for a request on a Preset
 *
 * <p>프리셋 생성/수정 요청 본문.
 */
@Data
public class PresetPayload {
    /** {@code activityTagless} */
    private String activityTagless;

    /** {@code category} */
    private String category;

    /** {@code componentTagless} */
    private String componentTagless;

    /** {@code description} */
    private String description;

    /** {@code filters} */
    private PresetFiltersPayload filters;

    /** {@code groupless} */
    private String groupless;

    /** {@code hasExternalCommunications} */
    private String hasExternalCommunications;

    /** {@code label} */
    private String label;

    /** {@code search} */
    private String search;
}
