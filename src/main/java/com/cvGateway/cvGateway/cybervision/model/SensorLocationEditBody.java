package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorLocationEditBody}.
 *
 * <p>센서 위치 수정 요청 본문 (WGS84 위도·경도).
 */
@Data
public class SensorLocationEditBody {
    /** 예: {@code 48.8583287} */
    private Double latitude;

    /** 예: {@code 2.2943839} */
    private Double longitude;
}
