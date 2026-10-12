package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code Component}.
 *
 * <p>JSON Component representation
 *
 * <p>컴포넌트. 네트워크에서 관측된 개별 통신 주체.
 */
@Data
public class Component {
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

    /** {@code extra_fields} */
    @JsonProperty("extra_fields")
    private List<ExtraField> extraFields;

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

    /** {@code reasons} */
    private List<String> reasons;

    /** {@code requestsCount} */
    private Long requestsCount;

    /** {@code sensorNames} */
    private List<String> sensorNames;

    /** {@code status} */
    private String status;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code userProperties} */
    private List<UserPropertyTuple> userProperties;

    /** {@code variablesCount} */
    private Long variablesCount;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
