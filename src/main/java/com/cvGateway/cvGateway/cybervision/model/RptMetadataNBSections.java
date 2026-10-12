package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code RptMetadataNBSections}.
 *
 * <p>리포트 메타데이터의 섹션 정의.
 */
@Data
public class RptMetadataNBSections {
    /** {@code section_id} */
    @JsonProperty("section_id")
    private String sectionId;

    /** {@code sub_section_ids} */
    @JsonProperty("sub_section_ids")
    private List<String> subSectionIds;
}
