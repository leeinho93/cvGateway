package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code FlowProperty}.
 *
 * <p>플로우에 부착된 속성.
 */
@Data
public class FlowProperty {
    /** {@code count} */
    private Long count;

    /** {@code first_seen} */
    @JsonProperty("first_seen")
    private Long firstSeen;

    /** {@code last_seen} */
    @JsonProperty("last_seen")
    private Long lastSeen;

    /** {@code property} */
    private String property;

    /** {@code value} */
    private String value;
}
