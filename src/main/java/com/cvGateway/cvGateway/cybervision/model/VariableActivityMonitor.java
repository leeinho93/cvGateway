package com.cvGateway.cvGateway.cybervision.model;

import java.time.OffsetDateTime;

import lombok.Data;

/**
 * {@code VariableActivityMonitor}.
 *
 * <p>변수 접근 액티비티 모니터링 정보.
 */
@Data
public class VariableActivityMonitor {
    /** {@code accessBy} */
    private String accessBy;

    /** {@code accessType} */
    private String accessType;

    /** {@code extraField} */
    private ExtraField extraField;

    /** {@code firstAccess} */
    private OffsetDateTime firstAccess;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code lastAccess} */
    private OffsetDateTime lastAccess;
}
