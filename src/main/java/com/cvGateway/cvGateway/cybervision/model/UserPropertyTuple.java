package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code UserPropertyTuple}.
 *
 * <p>사용자 정의 속성 튜플.
 */
@Data
public class UserPropertyTuple {
    /** {@code id} */
    private String id;

    /** {@code key} */
    private String key;

    /** {@code value} */
    private String value;
}
