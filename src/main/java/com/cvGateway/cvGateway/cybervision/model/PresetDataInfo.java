package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetDataInfo}.
 *
 * <p>프리셋 데이터의 갱신 메타데이터 (마지막 갱신 시각 등).
 */
@Data
public class PresetDataInfo {
    /** {@code forceRefreshRequested} */
    private Boolean forceRefreshRequested;

    /** {@code lastRefresh} */
    private Long lastRefresh;

    /** {@code lastRefreshDuration} */
    private Long lastRefreshDuration;

    /** {@code presetDataAvailable} */
    private Boolean presetDataAvailable;

    /** {@code refreshInProgress} */
    private Boolean refreshInProgress;
}
