package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Node}.
 *
 * <p>그래프의 노드.
 */
@Data
public class Node {
    /** {@code classes} */
    private String classes;

    /** {@code data} */
    private NodeData data;

    /** {@code group} */
    private String group;

    /** {@code locked} */
    private Boolean locked;

    /** {@code position} */
    private Position position;
}
