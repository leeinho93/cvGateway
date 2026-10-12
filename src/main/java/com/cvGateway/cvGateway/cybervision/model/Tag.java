package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Tag}.
 *
 * <p>태그. 컴포넌트·플로우에 자동 부여되는 분류 라벨.
 */
@Data
public class Tag {
    /** {@code category} */
    private Category category;

    /** {@code id} */
    private String id;

    /** {@code important} */
    private Boolean important;

    /** {@code label} */
    private String label;

    /** {@code type} */
    private String type;
}
