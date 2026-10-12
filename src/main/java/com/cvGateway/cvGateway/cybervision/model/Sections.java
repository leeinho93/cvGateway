package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code Sections}.
 *
 * <p>리포트 섹션.
 */
@Data
public class Sections {
    /** {@code checked} */
    private Boolean checked;

    /** {@code section_id} */
    @JsonProperty("section_id")
    private String sectionId;

    /** {@code section_name} */
    @JsonProperty("section_name")
    private String sectionName;

    /** {@code sub_sections} */
    @JsonProperty("sub_sections")
    private List<Subsections> subSections;
}
