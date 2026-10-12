package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code SectionsOfReportType}.
 *
 * <p>리포트 유형이 제공하는 섹션 목록.
 */
@Data
public class SectionsOfReportType {
    /** {@code section_id} */
    @JsonProperty("section_id")
    private String sectionId;

    /** {@code section_name} */
    @JsonProperty("section_name")
    private String sectionName;

    /** {@code sub_sections} */
    @JsonProperty("sub_sections")
    private List<SubSectionsofSections> subSections;

    /** {@code summary} */
    private String summary;
}
