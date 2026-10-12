package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Graph}.
 *
 * <p>그래프 전체 구조 (노드 + 간선).
 */
@Data
public class Graph {
    /** {@code edges} */
    private List<Edge> edges;

    /** {@code nodes} */
    private List<Node> nodes;
}
