package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * {@code SensorDetails}.
 *
 * <p>센서 상세.
 */
@Data
public class SensorDetails {
    /** {@code activeDiscoveryAddrs} */
    private Map<String, Object> activeDiscoveryAddrs;

    /** {@code activeDiscoveryConfig} */
    private List<ActiveDiscoveryInterface> activeDiscoveryConfig;

    /** {@code activeDiscoveryEnabled} */
    private Boolean activeDiscoveryEnabled;

    /** {@code activeDiscoveryInProgress} */
    private Boolean activeDiscoveryInProgress;

    /** {@code awaitingSync} */
    private Boolean awaitingSync;

    /** {@code centerId} */
    private String centerId;

    /** {@code centerIp} */
    private String centerIp;

    /** {@code certificateSerial} */
    private String certificateSerial;

    /** {@code collectInterface} */
    private String collectInterface;

    /** {@code creationTime} */
    private Long creationTime;

    /** {@code currentTime} */
    private Long currentTime;

    /** {@code currentTimeLastUpdate} */
    private Long currentTimeLastUpdate;

    /** {@code extensionCredentialsInvalid} */
    private Boolean extensionCredentialsInvalid;

    /** {@code filter} */
    private SensorFilter filter;

    /** {@code gateway} */
    private String gateway;

    /** {@code gpsBasedCoordinates} */
    private Boolean gpsBasedCoordinates;

    /** {@code hardwareType} */
    private String hardwareType;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private String ip;

    /** {@code lastGpsUpdate} */
    private Long lastGpsUpdate;

    /** {@code lastUpdate} */
    private Long lastUpdate;

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

    /** {@code netmask} */
    private String netmask;

    /** {@code recording} */
    private Boolean recording;

    /** {@code recordingLastStart} */
    private Long recordingLastStart;

    /** {@code recordingLastStop} */
    private Long recordingLastStop;

    /** serialNumber can be empty in some cases like CenterDPI */
    private String serialNumber;

    /** {@code snortEnabled} */
    private Boolean snortEnabled;

    /** {@code status} */
    private SensorStatus status;

    /** {@code uptime} */
    private Long uptime;

    /** {@code version} */
    private String version;
}
