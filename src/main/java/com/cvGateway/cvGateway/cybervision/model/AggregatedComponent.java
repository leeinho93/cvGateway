package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code AggregatedComponent}.
 *
 * <p>여러 컴포넌트를 하나로 합쳐 표현한 항목.
 */
@Data
public class AggregatedComponent {
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

    /** {@code mac} */
    private String mac;

    /** {@code normalizedProperties} */
    private List<PropertyTuple> normalizedProperties;

    /** {@code otherProperties} */
    private List<PropertyTuple> otherProperties;

    /** {@code tags} */
    private List<Tag> tags;
}
