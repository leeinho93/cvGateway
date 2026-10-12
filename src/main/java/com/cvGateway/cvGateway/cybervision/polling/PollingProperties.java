package com.cvGateway.cvGateway.cybervision.polling;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 폴링 동작 설정. {@code cyber-vision.polling.*} 을 바인딩한다.
 *
 * <p>주기는 밀리초이며 {@code fixedDelay} 로 동작한다. 즉 이전 회차가 끝난 시점부터 재는 간격이라,
 * Center 응답이 느려져도 호출이 겹쳐 쌓이지 않는다.
 */
@Data
@ConfigurationProperties(prefix = "cyber-vision.polling")
public class PollingProperties {

    /**
     * 폴링 사용 여부.
     *
     * <p>기본값이 {@code false} 다. Center 주소와 토큰이 설정되지 않은 환경에서 기동하면
     * 폴링이 계속 실패하며 로그만 더럽히기 때문이다. 접속 정보를 갖춘 뒤 켠다.
     */
    private boolean enabled = false;

    /** FAST 등급 주기. 기본 1분. */
    private long fast = 60_000L;

    /** NORMAL 등급 주기. 기본 5분. */
    private long normal = 300_000L;

    /** SLOW 등급 주기. 기본 30분. */
    private long slow = 1_800_000L;

    /** RARE 등급 주기. 기본 6시간. */
    private long rare = 21_600_000L;

    /**
     * 기동 후 첫 폴링까지 기다릴 시간. 기본 10초.
     *
     * <p>컨텍스트가 완전히 올라오기 전에 첫 호출이 나가는 것을 피한다.
     */
    private long initialDelay = 10_000L;

    /**
     * 프리셋 단위 데이터를 받아올 프리셋 ID 목록.
     *
     * <p>Cyber Vision 의 대시보드 계열 상당수가 {@code preset_id} 를 요구한다. 프리셋 전체를 돌면
     * 프리셋 수 × 엔드포인트 수만큼 호출이 곱해지므로, 화면이 실제로 쓰는 것만 여기에 지정한다.
     * 프리셋 ID 는 {@code GET /api/cache/presets} 로 확인할 수 있다.
     *
     * <p>비어 있으면 프리셋 단위 폴링을 하지 않는다. 전역 엔드포인트만 받아온다.
     */
    private List<String> presetIds = List.of();

    /**
     * 보안 인사이트를 받아올 프로토콜 유형.
     *
     * <p>스펙이 허용하는 값은 {@code dns}, {@code http}, {@code smb} 세 가지다.
     * 프리셋 하나당 여기 지정한 유형 수만큼 호출이 늘어난다.
     */
    private List<String> securityInsightTypes = List.of("dns", "http", "smb");
}
