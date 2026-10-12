package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Aggregation}.
 *
 * <p>집계 설정/결과.
 */
@Data
public class Aggregation {
    /** {@code components} */
    private List<AggregatedComponent> components;

    /** {@code type} */
    private String type;
}
