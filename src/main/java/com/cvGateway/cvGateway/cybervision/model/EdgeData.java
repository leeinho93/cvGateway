package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code EdgeData}.
 *
 * <p>그래프 간선에 부속된 데이터.
 */
@Data
public class EdgeData {
    /** {@code aggregatedEdges} */
    private List<String> aggregatedEdges;

    /** {@code id} */
    private String id;

    /** {@code source} */
    private String source;

    /** {@code target} */
    private String target;
}
