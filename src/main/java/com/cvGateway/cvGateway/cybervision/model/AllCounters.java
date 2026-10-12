package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code AllCounters}.
 *
 * <p>JSON representation for counters
 *
 * <p>대시보드용 카운터 묶음.
 */
@Data
public class AllCounters {
    /** {@code activity} */
    private ActivityCountResult activity;

    /** {@code credential} */
    private CredentialCountResult credential;

    /** {@code device} */
    private DeviceCountResult device;

    /** {@code differences} */
    private TotalDifferences differences;

    /** {@code event} */
    private EventCounts event;

    /** {@code group} */
    private GroupCountResult group;

    /** {@code orphanComponent} */
    private OrphanComponentCountResult orphanComponent;

    /** {@code plc} */
    private ProgramDownloadsCountResult plc;

    /** {@code riskScoreStats} */
    private RiskScoreStatsResult riskScoreStats;

    /** {@code tag} */
    private List<DetailedTag> tag;

    /** {@code variable} */
    private VariableCountResult variable;

    /** {@code vulnerability} */
    private VulnerabilityCountResult vulnerability;
}
