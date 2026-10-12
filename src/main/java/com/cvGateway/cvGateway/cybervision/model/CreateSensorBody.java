package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code CreateSensorBody}.
 *
 * <p>CreateSensorBody is the structure that contains every information for a request to create a sensor.
 *
 * <p>센서 등록 요청 본문.
 */
@Data
public class CreateSensorBody {
    /** {@code active_discovery_interfaces} */
    @JsonProperty("active_discovery_interfaces")
    private List<ActiveDiscoveryInterface> activeDiscoveryInterfaces;

    /** {@code centerip} */
    private String centerip;

    /** {@code custom} */
    private String custom;

    /** {@code gw} */
    private String gw;

    /** {@code ip} */
    private String ip;

    /** {@code mode} */
    private String mode;

    /** {@code model} */
    private String model;

    /** {@code name} */
    private String name;

    /** {@code netmask} */
    private String netmask;

    /** {@code rspan_capture} */
    @JsonProperty("rspan_capture")
    private Boolean rspanCapture;

    /** {@code serial} */
    private String serial;
}
