package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code NodeData}.
 *
 * <p>그래프 노드에 부속된 데이터.
 */
@Data
public class NodeData {
    /** {@code aggregatedComponent} */
    private List<String> aggregatedComponent;

    /** {@code aggregatedParent} */
    private List<String> aggregatedParent;

    /** {@code aggregationType} */
    private String aggregationType;

    /** {@code color} */
    private String color;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code name} */
    private String name;

    /** {@code parent} */
    private String parent;

    /** {@code vulnerabilityCount} */
    private Long vulnerabilityCount;
}
