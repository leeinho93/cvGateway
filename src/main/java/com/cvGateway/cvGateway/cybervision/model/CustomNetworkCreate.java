package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code CustomNetworkCreate}.
 *
 * <p>사용자 정의 네트워크 생성 항목. 벌크 생성/검증 요청의 배열 원소로 쓰인다.
 */
@Data
public class CustomNetworkCreate {
    /** 예: {@code true} */
    private Boolean duplicated;

    /** 예: {@code "10.0.0.0/8"} */
    private String ipRange;

    /** 예: {@code "OT Network-1"} */
    private String name;

    /** 예: {@code true} */
    private Boolean splitDevicesPerSensor;

    /** 예: {@code "OT Internal"} */
    private String type;

    /** 예: {@code 1} */
    private Integer vlanId;
}
