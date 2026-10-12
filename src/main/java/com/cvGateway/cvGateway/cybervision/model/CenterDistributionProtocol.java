package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code CenterDistributionProtocol}.
 *
 * <p>Center 별 프로토콜 분포.
 */
@Data
public class CenterDistributionProtocol {
    /** {@code centerId} */
    private String centerId;

    /** {@code centerName} */
    private String centerName;

    /** {@code total} */
    private List<TotalDistributionProtocol> total;
}
