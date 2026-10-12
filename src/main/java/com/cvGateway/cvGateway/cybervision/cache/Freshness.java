package com.cvGateway.cvGateway.cybervision.cache;

import java.time.Duration;
import java.time.Instant;

/**
 * 캐시 항목 하나가 언제 갱신됐고 직전 시도가 어땠는지.
 *
 * <p>값 자체는 항목별 {@link StateSlot} 이 들고 있고, 이 기록은 {@link FreshnessRegistry} 가
 * 모든 항목 것을 한곳에 모아 관리한다. 데이터와 메타데이터를 분리해 두면 "무엇이 언제 갱신됐고
 * 무엇이 실패 중인지"를 값 타입과 무관하게 한 번에 훑을 수 있다.
 *
 * @param fetchedAt     마지막으로 조회에 <b>성공</b>한 시각. 한 번도 성공하지 못했으면 {@code null}
 * @param lastAttemptAt 성공·실패를 통틀어 마지막으로 시도한 시각
 * @param lastError     마지막 실패 메시지. 직전 시도가 성공했으면 {@code null}
 */
public record Freshness(Instant fetchedAt, Instant lastAttemptAt, String lastError) {

    /** 아직 한 번도 조회하지 않은 상태. */
    public static Freshness never() {
        return new Freshness(null, null, null);
    }

    /** 성공 기록으로 교체한다. 실패 메시지는 지워진다. */
    public Freshness succeeded(Instant now) {
        return new Freshness(now, now, null);
    }

    /**
     * 실패를 기록한다.
     *
     * <p>{@code fetchedAt} 은 건드리지 않는다. 마지막으로 성공한 시점은 값이 얼마나 오래됐는지를
     * 판단하는 근거라, 실패했다고 잃어버리면 안 된다.
     */
    public Freshness failed(String message, Instant now) {
        return new Freshness(fetchedAt, now, message);
    }

    /** 한 번이라도 조회에 성공한 적 있는지. */
    public boolean everFetched() {
        return fetchedAt != null;
    }

    /** 직전 시도가 실패했는지. 성공 이력이 있으면 값 자체는 남아 있다. */
    public boolean isFailing() {
        return lastError != null;
    }

    /** 값이 얼마나 오래됐는지. 한 번도 성공하지 못했으면 {@code null}. */
    public Duration age() {
        return fetchedAt == null ? null : Duration.between(fetchedAt, Instant.now());
    }
}
