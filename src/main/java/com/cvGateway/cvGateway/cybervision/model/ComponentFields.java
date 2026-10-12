package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ComponentFields}.
 *
 * <p>컴포넌트 조회 시 반환할 필드 지정.
 */
@Data
public class ComponentFields {
    /** {@code aggregation} */
    private Aggregation aggregation;

    /** {@code ancestors} */
    private List<GroupAncestor> ancestors;

    /** {@code credentialsCount} */
    private Long credentialsCount;

    /** {@code customLabel} */
    private String customLabel;

    /** {@code device} */
    private LightDevice device;

    /** {@code eventsCount} */
    private Long eventsCount;

    /** {@code externalCommunicationsCount} */
    private Long externalCommunicationsCount;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code flowsCount} */
    private Long flowsCount;

    /** {@code flowsTags} */
    private List<Tag> flowsTags;

    /** {@code group} */
    private Group group;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private String ip;

    /** {@code label} */
    private String label;

    /** {@code lastActivity} */
    private Long lastActivity;

    /** {@code mac} */
    private String mac;

    /** {@code normalizedProperties} */
    private List<PropertyTuple> normalizedProperties;

    /** {@code originalLabel} */
    private String originalLabel;

    /** {@code otherProperties} */
    private List<PropertyTuple> otherProperties;

    /** {@code requestsCount} */
    private Long requestsCount;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code variablesCount} */
    private Long variablesCount;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
