package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GraphResponse}.
 *
 * <p>그래프 조회 응답.
 */
@Data
public class GraphResponse {
    /** {@code aggregatedView} */
    private GraphShowability aggregatedView;

    /** {@code allNetworkActivities} */
    private GraphShowability allNetworkActivities;

    /** {@code graph} */
    private Graph graph;

    /** {@code thresholds} */
    private Thresholds thresholds;

    /** {@code zonesAndConduits} */
    private ZonesAndConduitsShowability zonesAndConduits;
}
