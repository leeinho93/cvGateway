package com.cvGateway.cvGateway.cybervision.model;

import java.time.OffsetDateTime;

import lombok.Data;

/**
 * {@code AckInformations}.
 *
 * <p>취약점 승인(acknowledge) 이력. 누가 언제 어떤 사유로 승인했는지.
 */
@Data
public class AckInformations {
    /** {@code author} */
    private String author;

    /** {@code comment} */
    private String comment;

    /** {@code component} */
    private String component;

    /** {@code time} */
    private OffsetDateTime time;
}
