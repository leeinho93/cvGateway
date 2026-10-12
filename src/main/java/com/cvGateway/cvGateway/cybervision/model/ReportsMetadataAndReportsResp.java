package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ReportsMetadataAndReportsResp}.
 *
 * <p>리포트 메타데이터와 생성된 리포트를 함께 담은 응답.
 */
@Data
public class ReportsMetadataAndReportsResp {
    /** {@code metadataId} */
    private String metadataId;

    /** {@code reportsId} */
    private String reportsId;
}
