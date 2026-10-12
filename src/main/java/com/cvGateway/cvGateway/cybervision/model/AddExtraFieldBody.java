package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code AddExtraFieldBody}.
 *
 * <p>사용자 정의 속성 추가 요청 본문.
 */
@Data
public class AddExtraFieldBody {
    /** {@code label} */
    private String label;

    /** {@code value} */
    private String value;
}
