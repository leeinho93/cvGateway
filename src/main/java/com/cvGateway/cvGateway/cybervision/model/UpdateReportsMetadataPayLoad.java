package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code UpdateReportsMetadataPayLoad}.
 *
 * <p>리포트 메타데이터 수정 요청 본문.
 */
@Data
public class UpdateReportsMetadataPayLoad {
    /** {@code description} */
    private String description;

    /** {@code execute} */
    private Boolean execute;

    /** {@code name} */
    private String name;

    /** {@code output_type} */
    @JsonProperty("output_type")
    private List<String> outputType;
}
