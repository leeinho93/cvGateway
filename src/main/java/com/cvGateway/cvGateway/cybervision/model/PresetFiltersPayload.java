package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PresetFiltersPayload}.
 *
 * <p>contains all filters we want for the preset target by one request
 *
 * <p>프리셋 필터 설정 페이로드.
 */
@Data
public class PresetFiltersPayload {
    /** {@code centers} */
    private List<PresetCenterFilterPayload> centers;

    /** {@code components} */
    private List<PresetComponentFilterPayload> components;

    /** {@code devices} */
    private List<PresetDeviceFilterPayload> devices;

    /** {@code groups} */
    private List<PresetGroupFilterPayload> groups;

    /** {@code hasExternalCommunications} */
    private PresetHasExternalCommunicationsFilterPayload hasExternalCommunications;

    /** {@code networks} */
    private List<PresetNetworkFilterPayload> networks;

    /** {@code riskScores} */
    private List<PresetRiskScoreFilterPayload> riskScores;

    /** {@code sensors} */
    private List<PresetSensorFilterPayload> sensors;

    /** {@code tags} */
    private List<PresetTagFilterPayload> tags;
}
