package com.cvGateway.cvGateway.cybervision.web;

import java.time.Instant;

/**
 * 오류 응답 본문.
 *
 * @param status    HTTP 상태 코드
 * @param code      기계가 분기할 수 있는 오류 코드. 메시지 문구가 바뀌어도 이 값은 유지된다
 * @param message   사람이 읽을 설명과 조치 방법
 * @param timestamp 발생 시각
 */
public record ApiError(int status, String code, String message, Instant timestamp) {

    public static ApiError of(int status, String code, String message) {
        return new ApiError(status, code, message, Instant.now());
    }
}
