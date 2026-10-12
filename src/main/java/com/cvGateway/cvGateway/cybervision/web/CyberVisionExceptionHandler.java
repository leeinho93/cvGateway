package com.cvGateway.cvGateway.cybervision.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionApiException;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionNotConfiguredException;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionUnauthorizedException;
import com.cvGateway.cvGateway.cybervision.token.InvalidTokenException;

import lombok.extern.slf4j.Slf4j;

/**
 * Cyber Vision 연동에서 나는 예외를 HTTP 응답으로 바꾼다.
 *
 * <p>호출하는 쪽이 원인과 조치를 구분할 수 있도록 상태 코드와 {@code code} 를 나눠 둔다.
 * 특히 <b>토큰이 없는 것</b>과 <b>토큰이 거부된 것</b>은 조치가 달라 반드시 구분해야 한다.
 */
@Slf4j
@RestControllerAdvice
public class CyberVisionExceptionHandler {

    /**
     * 접속 정보(Center 주소·토큰)가 아직 주입되지 않았다.
     *
     * <p>게이트웨이가 아직 일할 준비가 안 된 상태이므로 503 으로 응답한다. 호출한 쪽의 잘못이
     * 아니라서 4xx 가 아니고, 접속 정보가 들어오면 저절로 해소되므로 영구 오류도 아니다.
     */
    @ExceptionHandler(CyberVisionNotConfiguredException.class)
    public ResponseEntity<ApiError> handleNotConfigured(CyberVisionNotConfiguredException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiError.of(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "CYBER_VISION_NOT_CONFIGURED",
                        e.getMessage()));
    }

    /**
     * 토큰은 보냈는데 Center 가 거부했다.
     *
     * <p>상류가 우리 자격증명을 거절한 것이므로 502 로 응답한다. 토큰이 만료됐거나 비활성화됐거나
     * 값 자체가 틀린 경우이며, 어느 쪽이든 새 토큰을 주입해야 풀린다.
     */
    @ExceptionHandler(CyberVisionUnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(CyberVisionUnauthorizedException e) {
        log.warn("Cyber Vision 이 토큰을 거부했다: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiError.of(
                        HttpStatus.BAD_GATEWAY.value(),
                        "CYBER_VISION_TOKEN_REJECTED",
                        "Cyber Vision 이 토큰을 거부했습니다. 만료·비활성화 여부를 확인하고"
                                + " POST /api/cyber-vision/connection 으로 새 토큰을 주입하세요."));
    }

    /** 그 밖의 Cyber Vision 호출 실패. 상류 문제이므로 502 로 넘긴다. */
    @ExceptionHandler(CyberVisionApiException.class)
    public ResponseEntity<ApiError> handleApiFailure(CyberVisionApiException e) {
        log.warn("Cyber Vision 호출 실패: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiError.of(
                        HttpStatus.BAD_GATEWAY.value(),
                        "CYBER_VISION_CALL_FAILED",
                        "Cyber Vision 호출에 실패했습니다 (status=%d)".formatted(e.getStatus())));
    }

    /**
     * 등록하려는 토큰을 Center 가 거부했다.
     *
     * <p>호출한 쪽이 잘못된 값을 보낸 것이므로 400 이다. 이미 저장된 토큰이 나중에 거부되는
     * {@link CyberVisionUnauthorizedException}(502) 과는 원인도 조치도 다르다.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException e) {
        return ResponseEntity.badRequest()
                .body(ApiError.of(
                        HttpStatus.BAD_REQUEST.value(),
                        "CYBER_VISION_TOKEN_INVALID",
                        e.getMessage()));
    }

    /** 잘못된 요청 값. 토큰을 빈 값으로 주입하려는 경우 등. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(ApiError.of(
                        HttpStatus.BAD_REQUEST.value(),
                        "INVALID_REQUEST",
                        e.getMessage()));
    }
}
