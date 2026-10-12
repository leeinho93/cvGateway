package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Variable}.
 *
 * <p>변수. 컴포넌트/디바이스에서 읽고 쓰는 데이터 지점.
 */
@Data
public class Variable {
    /** {@code accesses} */
    private List<VariableAccess> accesses;

    /** {@code category} */
    private String category;

    /** {@code differences} */
    private List<Difference> differences;

    /** {@code firstAccess} */
    private Long firstAccess;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code lastAccess} */
    private Long lastAccess;

    /** {@code newVariableAccess} */
    private Long newVariableAccess;

    /** {@code protocol} */
    private String protocol;

    /** {@code status} */
    private String status;

    /** {@code types} */
    private List<String> types;
}
