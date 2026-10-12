package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code IdPayloadForPresetRiskScore}.
 *
 * <p>프리셋 리스크 스코어 필터용 ID 페이로드.
 */
@Data
public class IdPayloadForPresetRiskScore {
    /** {@code id} */
    private String id;

    /** {@code max} */
    private Long max;

    /** {@code min} */
    private Long min;
}
