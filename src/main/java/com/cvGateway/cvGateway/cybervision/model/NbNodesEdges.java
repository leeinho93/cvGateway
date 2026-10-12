package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code NbNodesEdges}.
 *
 * <p>그래프의 노드·간선 개수.
 */
@Data
public class NbNodesEdges {
    /** {@code nbEdges} */
    private Long nbEdges;

    /** {@code nbNodes} */
    private Long nbNodes;
}
