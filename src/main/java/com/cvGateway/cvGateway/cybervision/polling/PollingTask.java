package com.cvGateway.cvGateway.cybervision.polling;

import java.util.function.Supplier;

import com.cvGateway.cvGateway.cybervision.cache.CyberVisionStateCache;
import com.cvGateway.cvGateway.cybervision.cache.StateKey;

/**
 * 캐시 항목 하나를 어떻게, 얼마나 자주 채울지에 대한 정의.
 *
 * @param key   채울 캐시 항목
 * @param tier  갱신 주기 등급
 * @param fetch Cyber Vision 조회 방법. 보통 Feign 클라이언트 메서드 참조
 * @param <T>   담기는 구조체 타입
 */
public record PollingTask<T>(StateKey<T> key, RefreshTier tier, Supplier<T> fetch) {

    /**
     * 조회해서 해당 항목의 슬롯에 넣는다.
     *
     * <p>키와 조회 결과의 타입이 이 레코드 안에서 {@code T} 로 묶여 있어, 스케줄러가
     * {@code PollingTask<?>} 목록을 그대로 순회해도 캐스팅이 필요 없다.
     *
     * @throws RuntimeException 조회가 실패하면 그대로 전파한다. 처리는 스케줄러 몫이다.
     */
    void runInto(CyberVisionStateCache cache) {
        cache.slot(key).set(fetch.get());
    }

    /** 조회 실패를 기록한다. 슬롯에 담긴 기존 값은 유지된다. */
    void recordFailure(CyberVisionStateCache cache, String message) {
        cache.slot(key).fail(message);
    }
}
