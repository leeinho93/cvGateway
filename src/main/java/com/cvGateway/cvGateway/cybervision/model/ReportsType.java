package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code ReportsType}.
 *
 * <p>리포트 유형(템플릿) 정보.
 */
@Data
public class ReportsType {
    /** {@code contents} */
    private Contents contents;

    /** {@code layout} */
    private String layout;

    /** {@code reportId} */
    private String reportId;

    /** {@code reportName} */
    private String reportName;

    /** {@code reportOutputFormat} */
    private List<String> reportOutputFormat;

    /** {@code report_type} */
    @JsonProperty("report_type")
    private String reportType;

    /** {@code title} */
    private String title;
}
