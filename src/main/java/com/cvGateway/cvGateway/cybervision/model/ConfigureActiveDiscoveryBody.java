package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code ConfigureActiveDiscoveryBody}.
 *
 * <p>센서 능동 탐색 설정 변경 요청 본문.
 */
@Data
public class ConfigureActiveDiscoveryBody {
    /** {@code active_discovery_interfaces} */
    @JsonProperty("active_discovery_interfaces")
    private List<ActiveDiscoveryInterface> activeDiscoveryInterfaces;

    /** {@code id} */
    private String id;
}
