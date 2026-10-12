package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code CustomNetworkUpdate}.
 *
 * <p>사용자 정의 네트워크 수정 항목. 생성 항목에 id 가 추가된 형태다.
 */
@Data
public class CustomNetworkUpdate {
    /** 예: {@code true} */
    private Boolean duplicated;

    /** 예: {@code "86bc937a-19cf-5f0a-9b28-1f9a8347590e"} */
    private String id;

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
