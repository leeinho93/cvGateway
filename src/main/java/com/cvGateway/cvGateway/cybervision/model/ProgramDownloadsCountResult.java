package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ProgramDownloadsCountResult}.
 *
 * <p>JSON representation for count
 *
 * <p>프로그램 다운로드 건수 집계.
 */
@Data
public class ProgramDownloadsCountResult {
    /** {@code created} */
    private Long created;

    /** {@code total} */
    private Long total;
}
