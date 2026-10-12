package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code SubSectionsofSections}.
 *
 * <p>리포트 섹션의 하위 섹션.
 */
@Data
public class SubSectionsofSections {
    /** {@code sub_section_id} */
    @JsonProperty("sub_section_id")
    private String subSectionId;

    /** {@code sub_section_name} */
    @JsonProperty("sub_section_name")
    private String subSectionName;
}
