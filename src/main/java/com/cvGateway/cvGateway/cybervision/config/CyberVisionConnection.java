package com.cvGateway.cvGateway.cybervision.config;

import java.time.Instant;

/**
 * Cyber Vision 접속 정보 한 벌.
 *
 * <p>주소와 토큰은 항상 짝으로 움직인다. Center 가 바뀌면 토큰도 그 Center 에서 발급한 것이어야
 * 하므로 한쪽만 바꿀 수 있게 두지 않고 통째로 교체한다.
 *
 * @param id         외부 시스템이 부여한 식별자. DB 의 키이기도 하다
 * @param centerUrl  Center 베이스 URL. 스킴 포함, 뒤 슬래시 없음
 * @param switchName 대상 스위치 이름
 * @param version    버전 문자열
 * @param token      API 토큰
 * @param updatedAt  주입된 시각
 */
public record CyberVisionConnection(
        String id,
        String centerUrl,
        String switchName,
        String version,
        String token,
        Instant updatedAt) {

    /**
     * 필수 값을 확인하고 URL 을 다듬어 만든다.
     *
     * @throws IllegalArgumentException id·주소·토큰 중 하나라도 비었거나 주소 형식이 아니면
     */
    public static CyberVisionConnection of(
            String id, String centerUrl, String switchName, String version, String token, Instant updatedAt) {

        return new CyberVisionConnection(
                require(id, "id"),
                normalizeUrl(centerUrl),
                trimOrNull(switchName),
                trimOrNull(version),
                require(token, "토큰"),
                updatedAt);
    }

    /** 확인용으로 가린 토큰. 앞 8자만 남긴다. */
    public String maskedToken() {
        String head = token.length() <= 8 ? token : token.substring(0, 8);
        return head + "***(" + token.length() + "자)";
    }

    private static String require(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + "이(가) 비어 있습니다");
        }
        return value.trim();
    }

    /** {@code switchName}, {@code version} 은 없어도 된다. 빈 문자열은 null 로 눕힌다. */
    private static String trimOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    /**
     * 뒤 슬래시를 떼어 경로를 이어 붙일 때 {@code //} 가 생기지 않게 한다.
     *
     * @throws IllegalArgumentException 비었거나 {@code http}/{@code https} 로 시작하지 않으면
     */
    private static String normalizeUrl(String url) {
        String trimmed = require(url, "Center 주소");
        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            throw new IllegalArgumentException(
                    "Center 주소는 http:// 또는 https:// 로 시작해야 합니다: " + trimmed);
        }
        while (trimmed.endsWith("/")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }
}
