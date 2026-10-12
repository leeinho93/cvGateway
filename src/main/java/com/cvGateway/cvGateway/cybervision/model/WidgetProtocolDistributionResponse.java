package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code WidgetProtocolDistributionResponse}.
 *
 * <p>JSON representation for counters
 *
 * <p>프로토콜 분포 위젯 응답.
 */
@Data
public class WidgetProtocolDistributionResponse {
    /** {@code centers} */
    private List<CenterDistributionProtocol> centers;

    /** {@code total} */
    private List<TotalDistributionProtocol> total;
}
