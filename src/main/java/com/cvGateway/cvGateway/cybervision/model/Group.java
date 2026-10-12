package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Group}.
 *
 * <p>Group item json representation
 *
 * <p>그룹. 컴포넌트·디바이스를 묶은 사용자 정의 단위.
 */
@Data
public class Group {
    /** {@code centerID} */
    private String centerID;

    /** {@code color} */
    private String color;

    /** {@code comments} */
    private String comments;

    /** {@code criticalness} */
    private Long criticalness;

    /** {@code description} */
    private String description;

    /** {@code groupIds} */
    private List<String> groupIds;

    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;

    /** {@code locked} */
    private Boolean locked;

    /** {@code parentId} */
    private String parentId;
}
