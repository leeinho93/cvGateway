package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code FlowContent}.
 *
 * <p>플로우에서 추출된 내용.
 */
@Data
public class FlowContent {
    /** {@code count} */
    private Long count;

    /** {@code property} */
    private String property;

    /** {@code value} */
    private String value;
}
