package com.cvGateway.cvGateway.cybervision.cache;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 캐시 항목 하나를 담는 구조체.
 *
 * <p>항목마다 슬롯 인스턴스가 하나씩 따로 있고, 각자 자기 값만 들고 있다. 신선도는 자기가 갖지 않고
 * 공용 {@link FreshnessRegistry} 에 기록한다.
 *
 * @param <T> 이 슬롯에 담기는 구조체 타입
 */
public final class StateSlot<T> {

    private final StateKey<T> key;
    private final FreshnessRegistry freshness;
    private final AtomicReference<T> value = new AtomicReference<>();

    StateSlot(StateKey<T> key, FreshnessRegistry freshness) {
        this.key = key;
        this.freshness = freshness;
    }

    /** 이 슬롯의 키. */
    public StateKey<T> key() {
        return key;
    }

    /**
     * 담긴 값.
     *
     * <p>한 번도 조회에 성공하지 못했으면 비어 있다. 직전 조회가 실패했더라도 그 전에 성공한 값이
     * 있으면 그 값이 나온다 — 일시적 장애에 화면이 통째로 비는 것을 막기 위함이다.
     * 값이 얼마나 오래됐는지는 {@link #freshness()} 로 확인한다.
     */
    public Optional<T> get() {
        return Optional.ofNullable(value.get());
    }

    /** 값이 없으면 대신 쓸 것을 지정해 꺼낸다. */
    public T orElse(T fallback) {
        T current = value.get();
        return current == null ? fallback : current;
    }

    /** 이 항목의 갱신 시각과 실패 여부. */
    public Freshness freshness() {
        return freshness.of(key);
    }

    /**
     * 조회 성공 결과로 값을 통째로 교체한다.
     *
     * <p>값을 먼저 바꾸고 신선도를 나중에 기록한다. 두 연산 사이에 읽는 쪽은 새 값에 직전 갱신
     * 시각을 보게 되는데, 실제보다 오래됐다고 판단하는 방향이라 안전하다. 그 반대였다면 아직
     * 반영되지 않은 값을 최신이라고 잘못 볼 수 있다.
     */
    public void set(T data) {
        value.set(data);
        freshness.recordSuccess(key);
    }

    /**
     * 조회 실패를 기록한다. 기존 값은 지우지 않는다.
     *
     * <p>Cyber Vision 이 잠시 응답하지 않는다고 해서 마지막으로 알던 자산 목록까지 버릴 이유는 없다.
     */
    public void fail(String message) {
        freshness.recordFailure(key, message);
    }

    @Override
    public String toString() {
        return "StateSlot[" + key.name() + "]";
    }
}
