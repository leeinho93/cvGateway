package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code EventOverview}.
 *
 * <p>이벤트 요약 정보.
 */
@Data
public class EventOverview {
    /** {@code category} */
    private String category;

    /** {@code centerId} */
    private String centerId;

    /** {@code centerLabel} */
    private String centerLabel;

    /** {@code date} */
    private Long date;

    /** {@code id} */
    private String id;

    /** {@code severity} */
    private String severity;

    /** {@code shortMessage} */
    private String shortMessage;
}
