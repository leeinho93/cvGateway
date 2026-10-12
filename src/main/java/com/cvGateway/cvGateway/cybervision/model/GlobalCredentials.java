package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code GlobalCredentials}.
 *
 * <p>전역 자격증명 조회 응답. 센서 공통 로그인 계정과 비밀번호를 담는다.
 */
@Data
public class GlobalCredentials {
    /** {@code sensor.security.login} */
    @JsonProperty("sensor.security.login")
    private String sensorSecurityLogin;

    /** {@code sensor.security.password} */
    @JsonProperty("sensor.security.password")
    private String sensorSecurityPassword;
}
