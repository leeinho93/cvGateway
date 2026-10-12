package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code WidgetVulnerableDevicesResponse}.
 *
 * <p>JSON representation for counters
 *
 * <p>취약 디바이스 위젯 응답.
 */
@Data
public class WidgetVulnerableDevicesResponse {
    /** {@code centers} */
    private List<TopCenter> centers;

    /** {@code total} */
    private Total total;
}
