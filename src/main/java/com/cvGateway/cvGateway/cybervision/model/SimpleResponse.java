package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code SimpleResponse}.
 *
 * <p>단순 처리 결과. Status(HTTP 코드)와 Msg(메시지)로 구성된다.
 */
@Data
public class SimpleResponse {
    /** {@code Msg} */
    @JsonProperty("Msg")
    private String msg;

    /** {@code Status} */
    @JsonProperty("Status")
    private Long status;
}
