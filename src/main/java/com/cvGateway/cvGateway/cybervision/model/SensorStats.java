package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code SensorStats}.
 *
 * <p>센서 통계 데이터.
 */
@Data
public class SensorStats {
    /** Indicates whether active discovery is enabled on the sensor (true) or not (false). */
    @JsonProperty("active_discovery_enabled")
    private Boolean activeDiscoveryEnabled;

    /** Indicates whether an active discovery process is currently in progress (true) or not (false). */
    @JsonProperty("active_discovery_in_progress")
    private Boolean activeDiscoveryInProgress;

    /** {@code active_discovery_stats} */
    @JsonProperty("active_discovery_stats")
    private ActiveDiscoveryStat activeDiscoveryStats;

    /** {@code cpu} */
    private List<GraphEntry> cpu;

    /** Current Percentage of CPU utilization */
    @JsonProperty("cpu_usage")
    private Double cpuUsage;

    /** {@code disk} */
    private List<GraphEntry> disk;

    /** {@code disk_io} */
    @JsonProperty("disk_io")
    private DiskIOEntries diskIo;

    /** Amount of data read from the disk, measured in Bytes */
    @JsonProperty("disk_io_read")
    private Long diskIoRead;

    /** Amount of data written to the disk, measured in Bytes */
    @JsonProperty("disk_io_write")
    private Long diskIoWrite;

    /** Current percentage of disk utilization */
    @JsonProperty("disk_usage")
    private Double diskUsage;

    /** Timestamp of the end requested time period. */
    private Long end;

    /** Indicates whether the host device credentials are invalid (true) or valid (false). */
    @JsonProperty("extension_credentials_invalid")
    private Boolean extensionCredentialsInvalid;

    /** {@code filter} */
    private SensorFilter filter;

    /** The IP Address of the sensor */
    private String ip;

    /** The MAC Address of the sensor */
    private String mac;

    /** The name of the sensor */
    private String name;

    /** {@code network} */
    private List<NetworkGraphEntries> network;

    /** {@code pkt} */
    private PktEntries pkt;

    /** The total number of packets received in last 5min */
    @JsonProperty("pkt_count")
    private Long pktCount;

    /** The total number of packets that were dropped in the last 5min */
    @JsonProperty("pkt_drop_count")
    private Long pktDropCount;

    /** The rate at which packets are received in the last 5min, measured in packets per second */
    @JsonProperty("pkt_rate")
    private Long pktRate;

    /** {@code ram} */
    private List<GraphEntry> ram;

    /** Current Percentage of memory utilization */
    @JsonProperty("ram_usage")
    private Double ramUsage;

    /** The serial number of the device */
    private String serial;

    /** Indicates whether Snort is enabled on the sensor (true) or not (false) */
    @JsonProperty("snort_enabled")
    private Boolean snortEnabled;

    /** Timestamp of the start requested time period */
    private Long start;

    /** The current connection status of the sensor */
    private String status;

    /** The system date of the sensor, measured in milliseconds */
    @JsonProperty("system_date")
    private Long systemDate;

    /** The duration for which the sensor has been active, measured in seconds */
    private Long uptime;

    /** Sensor version &lt;major&gt;.&lt;minor&gt;.&lt;increment&gt;+&lt;build&gt; */
    private String version;
}
