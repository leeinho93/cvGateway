package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code Subsections}.
 *
 * <p>하위 섹션 묶음.
 */
@Data
public class Subsections {
    /** {@code checked} */
    private Boolean checked;

    /** {@code sub_section_id} */
    @JsonProperty("sub_section_id")
    private String subSectionId;

    /** {@code sub_section_name} */
    @JsonProperty("sub_section_name")
    private String subSectionName;
}
