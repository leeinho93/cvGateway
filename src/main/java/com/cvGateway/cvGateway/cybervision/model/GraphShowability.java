package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GraphShowability}.
 *
 * <p>그래프 표시 가능 여부 설정.
 */
@Data
public class GraphShowability {
    /** {@code nbEdges} */
    private Long nbEdges;

    /** {@code nbNodes} */
    private Long nbNodes;

    /** {@code showable} */
    private Boolean showable;
}
