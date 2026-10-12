package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GroupRiskStruct}.
 *
 * <p>그룹 단위 리스크 집계.
 */
@Data
public class GroupRiskStruct {
    /** {@code criticalness} */
    private Long criticalness;

    /** {@code description} */
    private String description;

    /** {@code label} */
    private String label;

    /** {@code score} */
    private Long score;
}
