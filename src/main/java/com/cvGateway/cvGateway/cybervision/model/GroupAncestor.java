package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GroupAncestor}.
 *
 * <p>상위 그룹 참조. 그룹 계층 구조를 표현한다.
 */
@Data
public class GroupAncestor {
    /** {@code color} */
    private String color;

    /** {@code criticalness} */
    private Long criticalness;

    /** {@code depth} */
    private Long depth;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code locked} */
    private Boolean locked;
}
