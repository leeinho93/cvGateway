package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code FlowEndpoint}.
 *
 * <p>JSON FlowEndpoint representation
 *
 * <p>플로우의 한쪽 종단점.
 */
@Data
public class FlowEndpoint {
    /** {@code customLabel} */
    private String customLabel;

    /** {@code group} */
    private Group group;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code originalLabel} */
    private String originalLabel;
}
