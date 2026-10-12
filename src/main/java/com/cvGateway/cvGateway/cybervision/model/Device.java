package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Device}.
 *
 * <p>JSON Device representation
 *
 * <p>디바이스. 여러 컴포넌트를 묶은 논리적 장비 단위.
 */
@Data
public class Device {
    /** {@code activitiesCount} */
    private Long activitiesCount;

    /** {@code ancestors} */
    private List<GroupAncestor> ancestors;

    /** {@code bestAchievableScore} */
    private Long bestAchievableScore;

    /** {@code components} */
    private List<Component> components;

    /** {@code credentialsCount} */
    private Long credentialsCount;

    /** {@code customLabel} */
    private String customLabel;

    /** {@code deviceType} */
    private String deviceType;

    /** {@code deviceTypeDescription} */
    private String deviceTypeDescription;

    /** {@code eventsCount} */
    private Long eventsCount;

    /** {@code externalCommunicationsCount} */
    private Long externalCommunicationsCount;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code flowsTags} */
    private List<Tag> flowsTags;

    /** {@code group} */
    private Group group;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private List<String> ip;

    /** {@code isDevice} */
    private Boolean isDevice;

    /** {@code isRack} */
    private Boolean isRack;

    /** {@code label} */
    private String label;

    /** {@code lastActivity} */
    private Long lastActivity;

    /** {@code mac} */
    private List<String> mac;

    /** {@code normalizedProperties} */
    private List<PropertyTupleWithArrayOfValues> normalizedProperties;

    /** {@code originalLabel} */
    private String originalLabel;

    /** {@code otherProperties} */
    private List<PropertyTupleWithArrayOfValues> otherProperties;

    /** {@code riskScore} */
    private Long riskScore;

    /** {@code sensorNames} */
    private List<String> sensorNames;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code userProperties} */
    private List<UserPropertyTuple> userProperties;

    /** {@code variablesCount} */
    private Long variablesCount;

    /** {@code vulnerabilities} */
    private List<VulnerabilityDetail> vulnerabilities;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
