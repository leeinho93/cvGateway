package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code RiskScorePeriod}.
 *
 * <p>리스크 스코어 산출에 쓰이는 액티비티 태그 집계 기간 (초).
 */
@Data
public class RiskScorePeriod {
    /** 예: {@code 86400} */
    private Integer period;
}
