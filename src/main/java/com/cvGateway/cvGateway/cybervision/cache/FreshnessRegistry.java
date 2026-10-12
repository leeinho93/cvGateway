package com.cvGateway.cvGateway.cybervision.cache;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * 모든 캐시 항목의 신선도를 한곳에 모아 두는 맵.
 *
 * <p>값은 항목마다 {@link StateSlot} 이 각자 들고 있지만, 갱신 시각과 실패 여부는 여기 하나로
 * 모인다. 덕분에 값 타입을 몰라도 전체 항목의 상태를 한 번에 조회할 수 있다 — 헬스체크나
 * 운영 점검 화면이 이 맵만 보면 된다.
 *
 * <p>스레드 안전하다.
 */
@Component
public class FreshnessRegistry {

    private final Map<StateKey<?>, Freshness> entries = new ConcurrentHashMap<>();

    /** 항목의 신선도. 아직 조회한 적 없으면 {@link Freshness#never()}. */
    public Freshness of(StateKey<?> key) {
        return entries.getOrDefault(key, Freshness.never());
    }

    /** 조회 성공을 기록한다. */
    void recordSuccess(StateKey<?> key) {
        entries.merge(key, Freshness.never().succeeded(Instant.now()),
                (current, fresh) -> current.succeeded(fresh.lastAttemptAt()));
    }

    /** 조회 실패를 기록한다. 마지막 성공 시각은 유지된다. */
    void recordFailure(StateKey<?> key, String message) {
        Instant now = Instant.now();
        entries.merge(key, Freshness.never().failed(message, now),
                (current, ignored) -> current.failed(message, now));
    }

    /**
     * 전체 현황을 키 이름 순으로 돌려준다.
     *
     * <p>한 번도 조회하지 않은 항목은 들어 있지 않다.
     */
    public Map<String, Freshness> all() {
        Map<String, Freshness> result = new LinkedHashMap<>();
        entries.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(StateKey::name)))
                .forEach(entry -> result.put(entry.getKey().name(), entry.getValue()));
        return result;
    }
}
