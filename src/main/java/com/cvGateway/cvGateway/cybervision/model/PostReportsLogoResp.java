package com.cvGateway.cvGateway.cybervision.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Data;

/**
 * {@code PostReportsLogoResp}.
 *
 * <p>로고 업로드 응답. 로고 ID 를 담는다.
 */
@Data
public class PostReportsLogoResp {
    /** {@code logo_id} */
    @JsonProperty("logo_id")
    private List<Integer> logoId;
}
