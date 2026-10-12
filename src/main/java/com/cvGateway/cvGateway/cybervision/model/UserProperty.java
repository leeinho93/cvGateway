package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code UserProperty}.
 *
 * <p>UserProperty contains some user - defined props on groups
 *
 * <p>사용자 정의 속성.
 */
@Data
public class UserProperty {
    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code value} */
    private String value;
}
