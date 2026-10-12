package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code ActivitiesRiskStruct}.
 *
 * <p>액티비티 기준 리스크 집계.
 */
@Data
public class ActivitiesRiskStruct {
    /** {@code matchingTag} */
    private DetailedTag matchingTag;

    /** {@code mostImpactingActivity} */
    private Activity mostImpactingActivity;

    /** {@code score} */
    private Long score;
}
