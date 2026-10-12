package com.cvGateway.cvGateway.cybervision.config;

import lombok.Getter;

/**
 * Cyber Vision API 가 2xx 이외의 응답을 돌려줬을 때 발생한다.
 *
 * <p>{@link CyberVisionErrorDecoder} 가 생성한다.
 */
@Getter
public class CyberVisionApiException extends RuntimeException {

    /** 호출한 오퍼레이션 식별자. 예: {@code ActiveDiscoveryClient#listProfile()} */
    private final String operation;

    /** 응답 HTTP 상태 코드. */
    private final int status;

    /** 응답 본문. 읽을 수 없으면 빈 문자열. */
    private final String body;

    public CyberVisionApiException(String operation, int status, String body) {
        super("Cyber Vision API 호출 실패 [%s] status=%d body=%s".formatted(operation, status, body));
        this.operation = operation;
        this.status = status;
        this.body = body;
    }
}
