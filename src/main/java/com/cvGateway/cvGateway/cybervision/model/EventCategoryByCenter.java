package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code EventCategoryByCenter}.
 *
 * <p>Center 별 이벤트 카테고리 집계.
 */
@Data
public class EventCategoryByCenter {
    /** {@code anomaly} */
    private Long anomaly;

    /** {@code extension} */
    private Long extension;

    /** {@code id} */
    private List<Integer> id;

    /** {@code label} */
    private String label;

    /** {@code security} */
    private Long security;

    /** {@code signature} */
    private Long signature;

    /** {@code total} */
    private Long total;
}
