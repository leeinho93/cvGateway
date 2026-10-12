package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PropertyTuple}.
 *
 * <p>속성 튜플. 위치 기반 배열로 전달되는 속성 쌍.
 */
@Data
public class PropertyTuple {
    /** {@code key} */
    private String key;

    /** {@code value} */
    private String value;
}
