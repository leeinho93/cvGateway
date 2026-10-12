package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code CountDifferences}.
 *
 * <p>베이스라인 차이 개수 집계.
 */
@Data
public class CountDifferences {
    /** {@code changedActivity} */
    private Long changedActivity;

    /** {@code changedComponent} */
    private Long changedComponent;

    /** {@code newActivity} */
    private Long newActivity;

    /** {@code newComponent} */
    private Long newComponent;
}
