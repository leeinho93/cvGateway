package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PropertyTupleWithArrayOfValues}.
 *
 * <p>값이 배열인 속성 튜플.
 */
@Data
public class PropertyTupleWithArrayOfValues {
    /** {@code key} */
    private String key;

    /** {@code value} */
    private List<String> value;
}
