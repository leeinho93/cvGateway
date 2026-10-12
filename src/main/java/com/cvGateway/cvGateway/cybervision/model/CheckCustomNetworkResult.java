package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code CheckCustomNetworkResult}.
 *
 * <p>사용자 정의 네트워크 유효성 검증 결과.
 */
@Data
public class CheckCustomNetworkResult {
    /** {@code nbCmpToRemove} */
    private Long nbCmpToRemove;

    /** {@code nbExternalCommToRemove} */
    private Long nbExternalCommToRemove;
}
