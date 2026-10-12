package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code GroupPayload}.
 *
 * <p>GroupPayload is the structure used to map group creation or group update request payload
 *
 * <p>그룹 생성/수정 요청 본문.
 */
@Data
public class GroupPayload {
    /** {@code color} */
    private String color;

    /** {@code comments} */
    private String comments;

    /** {@code componentIds} */
    private List<String> componentIds;

    /** {@code criticalness} */
    private Long criticalness;

    /** {@code description} */
    private String description;

    /** {@code deviceIds} */
    private List<String> deviceIds;

    /** {@code groupIds} */
    private List<String> groupIds;

    /** {@code label} */
    private String label;

    /** {@code locked} */
    private Boolean locked;

    /** {@code parentId} */
    private String parentId;

    /** {@code userProperties} */
    private List<UserProperty> userProperties;
}
