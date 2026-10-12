package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code FlowDetail}.
 *
 * <p>플로우 상세.
 */
@Data
public class FlowDetail {
    /** {@code bytesCount} */
    private Long bytesCount;

    /** {@code direction} */
    private String direction;

    /** {@code dstPort} */
    private Long dstPort;

    /** {@code eventsCount} */
    private Long eventsCount;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code id} */
    private String id;

    /** {@code lastActivity} */
    private Long lastActivity;

    /** {@code left} */
    private ShortComponent left;

    /** {@code packetsCount} */
    private Long packetsCount;

    /** {@code properties} */
    private List<PropertyTuple> properties;

    /** {@code protocol} */
    private String protocol;

    /** {@code right} */
    private ShortComponent right;

    /** {@code sensor} */
    private Sensor sensor;

    /** {@code srcPort} */
    private Long srcPort;

    /** {@code tags} */
    private List<Tag> tags;
}
