package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code CreateReportsMetadataNBReq}.
 *
 * <p>리포트 메타데이터 생성 요청 본문.
 */
@Data
public class CreateReportsMetadataNBReq {
    /** {@code description} */
    private String description;

    /** {@code filter_id} */
    @JsonProperty("filter_id")
    private String filterId;

    /** {@code filter_type} */
    @JsonProperty("filter_type")
    private String filterType;

    /** {@code logo_id} */
    @JsonProperty("logo_id")
    private String logoId;

    /** {@code name} */
    private String name;

    /** {@code output_type} */
    @JsonProperty("output_type")
    private List<String> outputType;

    /** {@code sections} */
    private List<RptMetadataNBSections> sections;

    /** {@code type} */
    private String type;
}
