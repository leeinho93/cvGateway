package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code versions}.
 *
 * <p>구성 요소별 버전 목록. (스펙 원문의 소문자 시작 이름을 그대로 따른다)
 */
@Data
public class versions {
    /** {@code kdb} */
    private Long kdb;

    /** {@code sbs} */
    private String sbs;
}
