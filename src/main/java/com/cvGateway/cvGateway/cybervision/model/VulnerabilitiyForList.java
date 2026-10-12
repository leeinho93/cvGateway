package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;

/**
 * {@code VulnerabilitiyForList}.
 *
 * <p>취약점 목록 항목. (스펙 원문의 철자를 그대로 따른다)
 */
@Data
public class VulnerabilitiyForList {
    /** {@code CVSS} */
    @JsonProperty("CVSS")
    private Double cVSS;

    /** {@code CVSSVersion} */
    @JsonProperty("CVSSVersion")
    private Double cVSSVersion;

    /** {@code component} */
    private ShortComponent component;

    /** {@code countDeviceAffected} */
    private Long countDeviceAffected;

    /** {@code countOrphanAffected} */
    private Long countOrphanAffected;

    /** {@code cve} */
    private String cve;

    /** {@code device} */
    private LightDevice device;

    /** {@code fullDescription} */
    private String fullDescription;

    /** {@code id} */
    private String id;

    /** {@code links} */
    private List<VulnerabilityLink> links;

    /** {@code publishTime} */
    private OffsetDateTime publishTime;

    /** {@code solution} */
    private String solution;

    /** {@code summary} */
    private String summary;

    /** {@code title} */
    private String title;
}
