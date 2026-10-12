package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code UpdateExtraFieldBody}.
 *
 * <p>사용자 정의 속성 수정 요청 본문.
 */
@Data
public class UpdateExtraFieldBody {
    /** {@code label} */
    private String label;

    /** {@code value} */
    private String value;
}
