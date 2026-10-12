package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code AllCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>객체 종류별 전체 개수 집계.
 */
@Data
public class AllCountResult {
    /** {@code activity} */
    private ActivityCountResult activity;

    /** {@code credential} */
    private CredentialCountResult credential;

    /** {@code device} */
    private DeviceCountResult device;

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
