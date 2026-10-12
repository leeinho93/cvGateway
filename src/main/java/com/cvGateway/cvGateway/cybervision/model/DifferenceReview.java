package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DifferenceReview}.
 *
 * <p>차이 검토 처리 요청 본문.
 */
@Data
public class DifferenceReview {
    /** {@code accessType} */
    private String accessType;

    /** {@code comment} */
    private String comment;

    /** {@code id} */
    private String id;
}
