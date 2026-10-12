package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code UpdateStatus}.
 *
 * <p>갱신 처리 상태.
 */
@Data
public class UpdateStatus {
    /** {@code failed} */
    private Long failed;

    /** {@code na} */
    private Long na;

    /** {@code pending} */
    private Long pending;

    /** {@code updating} */
    private Long updating;

    /** {@code uploading} */
    private Long uploading;
}
