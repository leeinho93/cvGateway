package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code DifferenceDetails}.
 *
 * <p>베이스라인 차이의 상세 내역.
 */
@Data
public class DifferenceDetails {
    /** {@code differences} */
    private List<Difference> differences;

    /** {@code newVariableAccess} */
    private Long newVariableAccess;

    /** {@code status} */
    private String status;
}
