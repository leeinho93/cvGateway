package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code Category}.
 *
 * <p>Category json representation
 *
 * <p>이벤트 카테고리.
 */
@Data
public class Category {
    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code priority_order} */
    @JsonProperty("priority_order")
    private Long priorityOrder;
}
