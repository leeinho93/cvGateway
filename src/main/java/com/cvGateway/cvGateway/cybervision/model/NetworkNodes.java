package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code NetworkNodes}.
 *
 * <p>JSON Network Nodes representation
 *
 * <p>네트워크 노드(디바이스·컴포넌트) 목록.
 */
@Data
public class NetworkNodes {
    /** {@code activitiesCount} */
    private Long activitiesCount;

    /** {@code ancestors} */
    private List<GroupAncestor> ancestors;

    /** {@code customLabel} */
    private String customLabel;

    /** {@code device} */
    private String device;

    /** {@code deviceRule} */
    private String deviceRule;

    /** {@code deviceType} */
    private String deviceType;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code group} */
    private Group group;

    /** {@code hasExternalCommunication} */
    private Boolean hasExternalCommunication;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private List<String> ip;

    /** {@code isDevice} */
    private Boolean isDevice;

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

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code variablesCount} */
    private Long variablesCount;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
