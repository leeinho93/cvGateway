package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ExternalCommunication}.
 *
 * <p>외부망과의 통신 내역.
 */
@Data
public class ExternalCommunication {
    /** {@code direction} */
    private String direction;

    /** {@code externalIP} */
    private String externalIP;

    /** {@code hostname} */
    private String hostname;

    /** {@code id} */
    private String id;

    /** {@code internalIP} */
    private String internalIP;

    /** {@code lastSeen} */
    private Long lastSeen;

    /** {@code protocol} */
    private String protocol;

    /** {@code receivedByDevice} */
    private Long receivedByDevice;

    /** {@code sentByDevice} */
    private Long sentByDevice;

    /** {@code serverPort} */
    private String serverPort;
}
