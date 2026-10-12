package com.cvGateway.cvGateway.cybervision.token;

/**
 * 등록하려는 토큰을 Cyber Vision 이 거부했다.
 *
 * <p>토큰을 저장하기 전에 {@code GET /version} 으로 확인했는데 401 이 돌아온 경우다.
 * 잘못된 값이 DB 에 저장되는 것을 막기 위해 등록 자체를 실패시킨다.
 *
 * <p>이미 저장된 토큰이 나중에 거부되는 경우와는 다르다. 그쪽은
 * {@code CyberVisionUnauthorizedException} 이다.
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Cyber Vision 이 이 토큰을 거부했습니다. 값이 정확한지, 만료되거나 비활성화되지 않았는지 확인하세요.");
    }
}
