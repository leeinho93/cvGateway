package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code CredentialCountResult}.
 *
 * <p>자격증명 개수 집계 결과.
 */
@Data
public class CredentialCountResult {
    /** {@code active} */
    private Long active;

    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
