package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code VariableAccess}.
 *
 * <p>변수 접근 이력.
 */
@Data
public class VariableAccess {
    /** {@code component} */
    private VariableAccessComponent component;

    /** {@code device} */
    private VariableAccessComponent device;

    /** {@code firstAccess} */
    private Long firstAccess;

    /** {@code id} */
    private String id;

    /** {@code lastAccess} */
    private Long lastAccess;

    /** {@code types} */
    private List<String> types;
}
