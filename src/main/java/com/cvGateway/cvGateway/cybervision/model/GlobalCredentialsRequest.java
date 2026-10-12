package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

import lombok.Data;

/**
 * {@code GlobalCredentialsRequest}.
 *
 * <p>전역 자격증명 등록/수정 요청 본문.
 */
@Data
public class GlobalCredentialsRequest {
    /** {@code SensorsGlobalCredentials} */
    @JsonProperty("SensorsGlobalCredentials")
    private Map<String, Object> sensorsGlobalCredentials;
}
