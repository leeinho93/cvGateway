package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code BaselineComponentFields}.
 *
 * <p>베이스라인 비교에 쓰이는 컴포넌트 필드.
 */
@Data
public class BaselineComponentFields {
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

    /** {@code differences} */
    private List<Difference> differences;

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

    /** {@code newVariableAccess} */
    private Long newVariableAccess;

    /** {@code normalizedProperties} */
    private List<PropertyTuple> normalizedProperties;

    /** {@code originalLabel} */
    private String originalLabel;

    /** {@code otherProperties} */
    private List<PropertyTuple> otherProperties;

    /** {@code requestsCount} */
    private Long requestsCount;

    /** {@code status} */
    private String status;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code variablesCount} */
    private Long variablesCount;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
