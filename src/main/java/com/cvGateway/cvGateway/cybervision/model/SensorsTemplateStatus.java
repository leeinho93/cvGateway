package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code SensorsTemplateStatus}.
 *
 * <p>센서 템플릿 적용 상태.
 */
@Data
public class SensorsTemplateStatus {
    /** {@code deployed} */
    private Long deployed;

    /** {@code deploying} */
    private Long deploying;

    /** {@code failed} */
    private Long failed;

    /** {@code pending} */
    private Long pending;
}
