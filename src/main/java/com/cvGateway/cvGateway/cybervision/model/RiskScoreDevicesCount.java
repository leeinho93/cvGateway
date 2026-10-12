package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code RiskScoreDevicesCount}.
 *
 * <p>리스크 스코어 구간별 디바이스 수.
 */
@Data
public class RiskScoreDevicesCount {
    /** {@code centerId} */
    private List<Integer> centerId;

    /** {@code centerName} */
    private String centerName;

    /** {@code high} */
    private Long high;

    /** {@code low} */
    private Long low;

    /** {@code medium} */
    private Long medium;

    /** {@code total} */
    private Long total;
}
