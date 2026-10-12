package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ZonesAndConduitsShowability}.
 *
 * <p>존/컨듀잇 표시 가능 여부 설정.
 */
@Data
public class ZonesAndConduitsShowability {
    /** {@code enoughZones} */
    private Boolean enoughZones;

    /** {@code nbEdges} */
    private Long nbEdges;

    /** {@code nbNodes} */
    private Long nbNodes;

    /** {@code showable} */
    private Boolean showable;
}
