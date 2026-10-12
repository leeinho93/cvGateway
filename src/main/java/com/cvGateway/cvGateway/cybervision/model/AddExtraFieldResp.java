package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code AddExtraFieldResp}.
 *
 * <p>사용자 정의 속성 추가 응답.
 */
@Data
public class AddExtraFieldResp {
    /** {@code id} */
    private List<Integer> id;

    /** {@code label} */
    private String label;

    /** {@code value} */
    private String value;
}
