package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.Data;

/**
 * {@code ActiveDiscoveryStat}.
 *
 * <p>능동 탐색 통계. 마지막 스캔 시각 등을 담는다.
 */
@Data
public class ActiveDiscoveryStat {
    /**
     * The date of the last active discovery scan, or null if no scan has occurred
     *
     * <p>예: {@code "2023-09-15T09:43:42.297361Z"}
     */
    @JsonProperty("last_scan")
    private OffsetDateTime lastScan;

    /** The total number of errors encountered during active discovery */
    @JsonProperty("number_of_error")
    private Long numberOfError;

    /** The total number of active discovery scans performed */
    @JsonProperty("number_of_scan")
    private Long numberOfScan;

    /** Information about number of packets emitted per protocol during active discovery */
    @JsonProperty("packets_per_protocol")
    private Map<String, Object> packetsPerProtocol;
}
