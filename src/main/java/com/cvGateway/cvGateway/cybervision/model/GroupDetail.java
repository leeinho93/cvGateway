package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code GroupDetail}.
 *
 * <p>Group detail json representation
 *
 * <p>그룹 상세.
 */
@Data
public class GroupDetail {
    /** {@code activitiesCount} */
    private Long activitiesCount;

    /** {@code ancestors} */
    private List<GroupAncestor> ancestors;

    /** {@code centerID} */
    private String centerID;

    /** {@code color} */
    private String color;

    /** {@code comments} */
    private String comments;

    /** {@code componentIds} */
    private List<String> componentIds;

    /** {@code componentsTags} */
    private List<Tag> componentsTags;

    /** {@code criticalness} */
    private Long criticalness;

    /** {@code description} */
    private String description;

    /** {@code deviceIds} */
    private List<String> deviceIds;

    /** {@code devicesCount} */
    private Long devicesCount;

    /** {@code eventsCount} */
    private Long eventsCount;

    /** {@code flowsTags} */
    private List<Tag> flowsTags;

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

    /** {@code userProperties} */
    private List<ExtraField> userProperties;

    /** {@code vulnerabilitiesCount} */
    private Long vulnerabilitiesCount;
}
