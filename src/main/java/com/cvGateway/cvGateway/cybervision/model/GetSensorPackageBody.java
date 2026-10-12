package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code GetSensorPackageBody}.
 *
 * <p>GetSensorPackageBody is the structure that contains every information for a request to generate a
 * provisioning package.
 *
 * <p>센서 패키징 파일 생성 요청 본문.
 */
@Data
public class GetSensorPackageBody {
    /** {@code id} */
    private String id;
}
