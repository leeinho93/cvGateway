package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code PartialUpdate}.
 *
 * <p>see https://tools.ietf.org/html/rfc6902 for more information
 *
 * <p>RFC 6902(JSON Patch) 형식의 부분 수정 요청.
 */
@Data
public class PartialUpdate {
    /** {@code op} */
    private String op;

    /** {@code path} */
    private String path;

    /** {@code value} */
    private List<String> value;
}
