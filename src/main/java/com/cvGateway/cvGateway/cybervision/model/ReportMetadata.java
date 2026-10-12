package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code ReportMetadata}.
 *
 * <p>리포트 메타데이터. 리포트 생성 조건을 정의한다.
 */
@Data
public class ReportMetadata {
    /** {@code created_at} */
    @JsonProperty("created_at")
    private Long createdAt;

    /** {@code created_by} */
    @JsonProperty("created_by")
    private String createdBy;

    /** {@code cron_expression} */
    @JsonProperty("cron_expression")
    private String cronExpression;

    /** {@code description} */
    private String description;

    /** {@code filter_exist} */
    @JsonProperty("filter_exist")
    private Boolean filterExist;

    /** {@code filter_id} */
    @JsonProperty("filter_id")
    private String filterId;

    /** {@code filter_name} */
    @JsonProperty("filter_name")
    private String filterName;

    /** {@code filter_type} */
    @JsonProperty("filter_type")
    private String filterType;

    /** {@code id} */
    private List<Integer> id;

    /** {@code logo_id} */
    @JsonProperty("logo_id")
    private String logoId;

    /** {@code name} */
    private String name;

    /** {@code report_out_type} */
    @JsonProperty("report_out_type")
    private List<String> reportOutType;

    /** {@code report_type} */
    @JsonProperty("report_type")
    private String reportType;

    /** {@code reports} */
    private Reports reports;

    /** {@code sections} */
    private List<Sections> sections;

    /** {@code updated_at} */
    @JsonProperty("updated_at")
    private Long updatedAt;

    /** {@code updated_by} */
    @JsonProperty("updated_by")
    private String updatedBy;
}
