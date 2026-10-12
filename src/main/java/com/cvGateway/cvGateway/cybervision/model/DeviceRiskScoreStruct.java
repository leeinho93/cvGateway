package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DeviceRiskScoreStruct}.
 *
 * <p>디바이스 리스크 스코어 구조체.
 */
@Data
public class DeviceRiskScoreStruct {
    /** {@code activitiesRisk} */
    private ActivitiesRiskStruct activitiesRisk;

    /** {@code deviceEngineLastRun} */
    private Long deviceEngineLastRun;

    /** {@code deviceTypeRisk} */
    private DeviceTypeRiskStruct deviceTypeRisk;

    /** {@code groupRisk} */
    private GroupRiskStruct groupRisk;

    /** {@code vulnerabilitiesRisk} */
    private VulnerabilitiesRiskStruct vulnerabilitiesRisk;
}
