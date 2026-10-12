package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code BaselineDifference}.
 *
 * <p>베이스라인과 현재 상태의 차이 항목.
 */
@Data
public class BaselineDifference {
    /** {@code baselineId} */
    private String baselineId;

    /** {@code detectionTime} */
    private Long detectionTime;

    /** {@code id} */
    private String id;

    /** {@code key} */
    private String key;

    /** {@code targetId} */
    private String targetId;

    /** {@code type} */
    private String type;

    /** {@code value} */
    private String value;
}
