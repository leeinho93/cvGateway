package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code Preset}.
 *
 * <p>Preset Json representation
 *
 * <p>프리셋. 조회 범위를 미리 정의한 필터 묶음.
 */
@Data
public class Preset {
    /** {@code activityTagless} */
    private String activityTagless;

    /** {@code badges} */
    private List<PresetBadge> badges;

    /** {@code baselines} */
    private List<Baseline> baselines;

    /** {@code category} */
    private Category category;

    /** {@code componentTagless} */
    private String componentTagless;

    /** {@code creatorEmail} */
    private String creatorEmail;

    /** {@code custom} */
    private Boolean custom;

    /** {@code description} */
    private String description;

    /** {@code filters} */
    private PresetFilters filters;

    /** {@code groupless} */
    private String groupless;

    /** {@code hasExternalCommunications} */
    private String hasExternalCommunications;

    /** {@code id} */
    private String id;

    /** {@code imported} */
    private Boolean imported;

    /** {@code label} */
    private String label;

    /** {@code lastUpdate} */
    private Long lastUpdate;

    /** {@code operationalStarred} */
    private Boolean operationalStarred;

    /** {@code priority_order} */
    @JsonProperty("priority_order")
    private Long priorityOrder;

    /** {@code search} */
    private String search;

    /** {@code securityStarred} */
    private Boolean securityStarred;
}
