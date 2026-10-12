package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorOverview}.
 *
 * <p>센서 요약 정보. 목록 조회용.
 */
@Data
public class SensorOverview {
    /** {@code centerId} */
    private String centerId;

    /** {@code certificateSerial} */
    private String certificateSerial;

    /** {@code creationTime} */
    private Long creationTime;

    /** {@code filter} */
    private SensorFilter filter;

    /** {@code id} */
    private String id;

    /** 예: {@code 48.8583287} */
    private Double latitude;

    /** 예: {@code 2.2943839} */
    private Double longitude;

    /** {@code mac} */
    private String mac;

    /** {@code model} */
    private String model;

    /** name is empty if not user defined, in that case, serialNumber can be used as a fallback name */
    private String name;

    /** serialNumber can be empty in some cases like CenterDPI */
    private String serialNumber;

    /** {@code status} */
    private SensorStatus status;
}
