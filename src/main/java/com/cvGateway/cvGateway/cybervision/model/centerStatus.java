package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code centerStatus}.
 *
 * <p>Center 상태. (스펙 원문의 소문자 시작 이름을 그대로 따른다)
 */
@Data
public class centerStatus {
    /** {@code connectivityStatus} */
    private String connectivityStatus;

    /** {@code enrollmentStatus} */
    private String enrollmentStatus;

    /** {@code lastSeenInSeconds} */
    private Long lastSeenInSeconds;
}
