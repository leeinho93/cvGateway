package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActiveDiscoveryProfile}.
 *
 * <p>능동 탐색 프로필. 스캔 대상(IP 범위·센서)과 실행 일정을 정의한다.
 */
@Data
public class ActiveDiscoveryProfile {
    /** {@code allSensorSelected} */
    private Boolean allSensorSelected;

    /** {@code endTime} */
    private Long endTime;

    /** {@code id} */
    private String id;

    /** {@code ipRanges} */
    private List<String> ipRanges;

    /** {@code ipTargets} */
    private List<String> ipTargets;

    /** {@code lastStartTimeScan} */
    private Long lastStartTimeScan;

    /** {@code lastStartTimeScanStatus} */
    private String lastStartTimeScanStatus;

    /** {@code name} */
    private String name;

    /** {@code policyId} */
    private String policyId;

    /** {@code presets} */
    private List<ActiveDiscoveryProfilePreset> presets;

    /** {@code runOnce} */
    private String runOnce;

    /** {@code scanFrequency} */
    private String scanFrequency;

    /** {@code schedulingStatus} */
    private String schedulingStatus;

    /** {@code sensors} */
    private List<SensorLite> sensors;

    /** {@code startTime} */
    private Long startTime;
}
