package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code WidgetEventByCategory}.
 *
 * <p>카테고리별 이벤트 위젯 데이터.
 */
@Data
public class WidgetEventByCategory {
    /** {@code centers} */
    private List<EventCategoryByCenter> centers;

    /** {@code total} */
    private List<TotalEventByCategory> total;
}
