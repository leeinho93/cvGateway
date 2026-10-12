package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Edge}.
 *
 * <p>그래프의 간선. 두 노드 사이 연결을 나타낸다.
 */
@Data
public class Edge {
    /** {@code classes} */
    private String classes;

    /** {@code data} */
    private EdgeData data;

    /** {@code group} */
    private String group;
}
