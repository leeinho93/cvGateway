package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetHighlight}.
 *
 * <p>즐겨찾기 프리셋의 하이라이트 정보.
 */
@Data
public class PresetHighlight {
    /** {@code categoryLabel} */
    private String categoryLabel;

    /** {@code devices} */
    private Long devices;

    /** {@code events} */
    private Long events;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code lastRefresh} */
    private Long lastRefresh;

    /** {@code orphans} */
    private Long orphans;

    /** {@code riskScore} */
    private Double riskScore;

    /** {@code vulnerabilities} */
    private Long vulnerabilities;
}
