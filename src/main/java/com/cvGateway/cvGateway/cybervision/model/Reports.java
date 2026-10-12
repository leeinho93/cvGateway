package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code Reports}.
 *
 * <p>생성된 리포트 정보.
 */
@Data
public class Reports {
    /** {@code error} */
    private String error;

    /** {@code last_run_at} */
    @JsonProperty("last_run_at")
    private Long lastRunAt;

    /** {@code last_run_by} */
    @JsonProperty("last_run_by")
    private String lastRunBy;

    /** {@code logo_id} */
    @JsonProperty("logo_id")
    private String logoId;

    /** {@code reports_id} */
    @JsonProperty("reports_id")
    private List<Integer> reportsId;

    /** {@code reports_outputs} */
    @JsonProperty("reports_outputs")
    private List<DocumentDetails> reportsOutputs;

    /** {@code status} */
    private String status;
}
