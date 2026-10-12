package com.cvGateway.cvGateway.cybervision.web;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.cvGateway.cvGateway.cybervision.cache.Freshness;

/**
 * 캐시에서 꺼낸 값과 그 값이 언제 것인지를 함께 담는 응답.
 *
 * <p>이 API 는 Cyber Vision 을 즉석에서 호출하지 않고 주기적으로 받아 둔 값을 돌려준다.
 * 따라서 화면 쪽에서는 "언제 받아온 값인가"를 알아야 하므로 데이터와 함께 신선도를 내려준다.
 *
 * <p>아직 한 번도 받아오지 못했더라도 HTTP 상태는 200 이다. 게이트웨이 자신은 정상이고
 * 상류 데이터가 없다는 사실 자체가 응답 내용이기 때문이다. {@code available} 로 구분한다.
 *
 * @param available   한 번이라도 조회에 성공해 보여 줄 값이 있는지
 * @param fetchedAt   마지막으로 조회에 성공한 시각. 한 번도 없으면 {@code null}
 * @param ageSeconds  값이 얼마나 오래됐는지(초). 값이 없으면 {@code null}
 * @param failing     직전 폴링이 실패했는지. {@code true} 여도 이전 값은 남아 있을 수 있다
 * @param lastError   직전 폴링 실패 메시지
 * @param total       필터를 적용하기 전 전체 개수. 목록이 아니면 {@code null}
 * @param count       실제로 응답에 담긴 개수. 목록이 아니면 {@code null}
 * @param data        값. 없으면 {@code null}
 */
public record CachedResponse<T>(
        boolean available,
        Instant fetchedAt,
        Long ageSeconds,
        boolean failing,
        String lastError,
        Integer total,
        Integer count,
        T data) {

    /** 목록이 아닌 단일 값(집계·버전 등)에 대한 응답. */
    public static <T> CachedResponse<T> of(T data, Freshness freshness) {
        return new CachedResponse<>(
                data != null,
                freshness.fetchedAt(),
                seconds(freshness.age()),
                freshness.isFailing(),
                freshness.lastError(),
                null,
                null,
                data);
    }

    /**
     * 목록에 대한 응답.
     *
     * @param page  필터·페이징을 거친 결과
     * @param total 필터 적용 전 전체 개수
     */
    public static <E> CachedResponse<List<E>> ofList(List<E> page, int total, Freshness freshness) {
        return new CachedResponse<>(
                freshness.everFetched(),
                freshness.fetchedAt(),
                seconds(freshness.age()),
                freshness.isFailing(),
                freshness.lastError(),
                total,
                page.size(),
                page);
    }

    /** 아직 한 번도 받아오지 못한 목록에 대한 응답. */
    public static <E> CachedResponse<List<E>> emptyList(Freshness freshness) {
        return new CachedResponse<>(
                false,
                null,
                null,
                freshness.isFailing(),
                freshness.lastError(),
                null,
                null,
                null);
    }

    private static Long seconds(Duration age) {
        return age == null ? null : age.toSeconds();
    }
}
