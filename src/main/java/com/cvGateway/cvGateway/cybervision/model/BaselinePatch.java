package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code BaselinePatch}.
 *
 * <p>베이스라인 부분 수정 요청 본문 (라벨·설명).
 */
@Data
public class BaselinePatch {
    /** {@code description} */
    private String description;

    /** {@code label} */
    private String label;
}
