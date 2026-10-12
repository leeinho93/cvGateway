package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code DetailedTag}.
 *
 * <p>DetailedTag detailed tag serialization
 *
 * <p>태그 상세. 라벨과 분류 정보를 포함한다.
 */
@Data
public class DetailedTag {
    /** {@code category} */
    private Category category;

    /** {@code description} */
    private String description;

    /** {@code difference} */
    private Difference difference;

    /** {@code domain} */
    private String domain;

    /** {@code id} */
    private String id;

    /** {@code important} */
    private Boolean important;

    /** {@code label} */
    private String label;

    /** {@code nbOccurences} */
    private Long nbOccurences;

    /** {@code riskScoreDescription} */
    private String riskScoreDescription;

    /** {@code type} */
    private String type;
}
