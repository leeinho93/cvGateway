package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Flow}.
 *
 * <p>JSON flow representation
 *
 * <p>플로우. 출발지·목적지·포트·프로토콜로 식별되는 개별 통신.
 */
@Data
public class Flow {
    /** {@code bytesCount} */
    private Long bytesCount;

    /** {@code direction} */
    private String direction;

    /** {@code dstPort} */
    private Long dstPort;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code id} */
    private String id;

    /** {@code lastActivity} */
    private Long lastActivity;

    /** {@code left} */
    private FlowEndpoint left;

    /** {@code packetsCount} */
    private Long packetsCount;

    /** {@code protocol} */
    private String protocol;

    /** {@code right} */
    private FlowEndpoint right;

    /** {@code srcPort} */
    private Long srcPort;

    /** {@code tags} */
    private List<Tag> tags;
}
