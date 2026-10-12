package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PresetFilters}.
 *
 * <p>list all filters in a Preset
 *
 * <p>프리셋에 적용된 필터 전체.
 */
@Data
public class PresetFilters {
    /** {@code centers} */
    private List<PresetCenterFilter> centers;

    /** {@code groups} */
    private List<PresetGroupFilter> groups;

    /** {@code networks} */
    private List<PresetNetworkFilter> networks;

    /** {@code riskScores} */
    private List<PresetRiskScoreFilter> riskScores;

    /** {@code sensors} */
    private List<PresetSensorFilter> sensors;

    /** {@code tags} */
    private List<PresetTagFilter> tags;
}
