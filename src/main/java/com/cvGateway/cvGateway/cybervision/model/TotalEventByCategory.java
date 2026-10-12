package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code TotalEventByCategory}.
 *
 * <p>카테고리별 이벤트 총계.
 */
@Data
public class TotalEventByCategory {
    /** {@code category} */
    private String category;

    /** {@code count} */
    private Long count;
}
