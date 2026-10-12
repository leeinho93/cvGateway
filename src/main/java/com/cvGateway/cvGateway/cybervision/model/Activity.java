package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Activity}.
 *
 * <p>액티비티. 두 네트워크 노드 사이 통신을 묶은 단위.
 */
@Data
public class Activity {
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
    private ShortComponent left;

    /** {@code newVariableAccess} */
    private Long newVariableAccess;

    /** {@code packetsCount} */
    private Long packetsCount;

    /** {@code right} */
    private ShortComponent right;

    /** {@code status} */
    private String status;

    /** {@code tags} */
    private List<Tag> tags;

    /** {@code variables} */
    private List<VariableActivityMonitor> variables;

    /** {@code variablesCount} */
    private Long variablesCount;
}
