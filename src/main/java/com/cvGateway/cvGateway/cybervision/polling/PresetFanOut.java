package com.cvGateway.cvGateway.cybervision.polling;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

/**
 * 프리셋마다 한 번씩 호출해 결과를 프리셋 ID 를 키로 하는 맵으로 모은다.
 *
 * <p>Cyber Vision 의 대시보드 계열은 {@code preset_id} 를 요구하는데, 캐시 항목을 프리셋마다
 * 따로 만들면 키가 동적으로 늘어난다. 그래서 하나의 캐시 항목에 맵으로 담고, 그 맵을 만드는
 * 일을 여기서 처리한다.
 *
 * <p>프리셋 하나가 실패해도 나머지는 살린다. 프리셋이 삭제됐거나 권한이 없어 404 가 나는 경우
 * 하나 때문에 전체 항목이 비는 것을 막기 위함이다. 다만 <b>전부 실패하면 예외를 던진다</b> —
 * 그건 개별 프리셋 문제가 아니라 연결·인증 문제이므로 폴링 실패로 기록돼야 한다.
 */
@Slf4j
final class PresetFanOut {

    private PresetFanOut() {
    }

    /** 프리셋별로 한 번 호출한다. */
    static <T> Supplier<Map<String, T>> byPreset(List<String> presetIds, Function<String, T> fetch) {
        return () -> {
            Map<String, T> result = new LinkedHashMap<>();
            RuntimeException lastFailure = null;

            for (String presetId : presetIds) {
                try {
                    result.put(presetId, fetch.apply(presetId));
                } catch (RuntimeException e) {
                    lastFailure = e;
                    log.warn("프리셋 {} 조회 실패: {}", presetId, e.getMessage());
                }
            }
            return requireAnySuccess(result, presetIds, lastFailure);
        };
    }

    /**
     * 프리셋 × 프로토콜 유형 조합마다 한 번 호출한다.
     *
     * <p>결과는 {@code 프리셋 ID -> 유형 -> 값} 으로 중첩된다.
     */
    static <T> Supplier<Map<String, Map<String, T>>> byPresetAndType(
            List<String> presetIds, List<String> types, BiFunction<String, String, T> fetch) {

        return () -> {
            Map<String, Map<String, T>> result = new LinkedHashMap<>();
            RuntimeException lastFailure = null;

            for (String presetId : presetIds) {
                Map<String, T> perType = new LinkedHashMap<>();
                for (String type : types) {
                    try {
                        perType.put(type, fetch.apply(presetId, type));
                    } catch (RuntimeException e) {
                        lastFailure = e;
                        log.warn("프리셋 {} / 유형 {} 조회 실패: {}", presetId, type, e.getMessage());
                    }
                }
                if (!perType.isEmpty()) {
                    result.put(presetId, perType);
                }
            }
            return requireAnySuccess(result, presetIds, lastFailure);
        };
    }

    /**
     * 하나라도 성공했으면 그대로 돌려주고, 전부 실패했으면 마지막 예외를 다시 던진다.
     *
     * <p>조회할 프리셋이 애초에 없으면 빈 맵을 그대로 돌려준다. 설정하지 않은 것은 실패가 아니다.
     */
    private static <V> Map<String, V> requireAnySuccess(
            Map<String, V> result, List<String> presetIds, RuntimeException lastFailure) {

        if (result.isEmpty() && !presetIds.isEmpty() && lastFailure != null) {
            throw lastFailure;
        }
        return result;
    }
}
