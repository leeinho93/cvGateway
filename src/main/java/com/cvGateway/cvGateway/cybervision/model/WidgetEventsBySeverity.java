package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code WidgetEventsBySeverity}.
 *
 * <p>심각도별 이벤트 위젯 데이터.
 */
@Data
public class WidgetEventsBySeverity {
    /** {@code centers} */
    private List<EventByCenter> centers;

    /** {@code events} */
    private List<EventOverview> events;
}
