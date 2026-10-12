package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code VariableMonitor}.
 *
 * <p>변수 모니터링 설정/결과.
 */
@Data
public class VariableMonitor {
    /** {@code accessType} */
    private String accessType;

    /** {@code category} */
    private String category;

    /** {@code client} */
    private VariableAccessComponent client;

    /** {@code difference} */
    private Difference difference;

    /** {@code firstAccess} */
    private Long firstAccess;

    /** {@code id} */
    private List<Integer> id;

    /** {@code lastAccess} */
    private Long lastAccess;

    /** {@code name} */
    private String name;

    /** {@code protocol} */
    private String protocol;

    /** {@code types} */
    private String types;
}
