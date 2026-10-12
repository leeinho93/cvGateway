package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code BaselineCreate}.
 *
 * <p>베이스라인 생성 요청 본문.
 */
@Data
public class BaselineCreate {
    /** {@code description} */
    private String description;

    /** {@code from} */
    private String from;

    /** {@code label} */
    private String label;

    /** {@code presetId} */
    private String presetId;

    /** {@code to} */
    private String to;
}
