package com.cvGateway.cvGateway.cybervision.config;

/**
 * Center 주소와 토큰이 아직 주입되지 않은 상태에서 Cyber Vision 을 호출하려 했을 때 발생한다.
 *
 * <p>접속 정보는 외부 시스템이 {@code POST /api/cyber-vision/connection} 으로 넣어 준다.
 * DB 에 남은 값이 있으면 기동 시 자동으로 올라오지만, 한 번도 등록된 적 없거나 지워졌으면
 * 주입되기 전까지 이 예외가 난다.
 *
 * <p>{@link CyberVisionUnauthorizedException} 과 구분한다. 그쪽은 토큰을 보냈는데 Center 가
 * 거부한 경우이고, 이쪽은 아예 보낼 주소나 토큰이 없는 경우다. 원인과 조치가 다르다.
 */
public class CyberVisionNotConfiguredException extends RuntimeException {

    public CyberVisionNotConfiguredException() {
        super("Cyber Vision 접속 정보가 없습니다."
                + " POST /api/cyber-vision/connection 으로 Center 주소와 토큰을 먼저 등록하세요.");
    }
}
