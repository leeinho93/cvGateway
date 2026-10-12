package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ActivityBetweenEndpoints}.
 *
 * <p>특정 엔드포인트 쌍 사이의 액티비티 상세.
 */
@Data
public class ActivityBetweenEndpoints {
    /** {@code bytesCount} */
    private Long bytesCount;

    /** {@code differences} */
    private List<Difference> differences;

    /** {@code direction} */
    private String direction;

    /** {@code eventsCount} */
    private Long eventsCount;

    /** {@code firstActivity} */
    private Long firstActivity;

    /** {@code flowCount} */
    private Long flowCount;

    /** {@code id} */
    private String id;

    /** {@code lastActivity} */
    private Long lastActivity;

    /** {@code left} */
    private Object left;

    /** {@code newVariableAccess} */
    private Long newVariableAccess;

    /** {@code packetsCount} */
    private Long packetsCount;

    /** {@code right} */
    private Object right;

    /** {@code status} */
    private String status;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code variables} */
    private List<VariableActivityMonitor> variables;

    /** {@code variablesCount} */
    private Long variablesCount;
}
