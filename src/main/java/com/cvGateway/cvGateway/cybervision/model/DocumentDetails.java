package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * {@code DocumentDetails}.
 *
 * <p>문서 메타 정보.
 */
@Data
public class DocumentDetails {
    /** {@code document_id} */
    @JsonProperty("document_id")
    private String documentId;

    /** {@code document_name} */
    @JsonProperty("document_name")
    private String documentName;

    /** {@code document_type} */
    @JsonProperty("document_type")
    private String documentType;
}
