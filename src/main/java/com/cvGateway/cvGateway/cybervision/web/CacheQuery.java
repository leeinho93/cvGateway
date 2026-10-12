package com.cvGateway.cvGateway.cybervision.web;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 캐시 목록에 필터와 페이징을 적용하는 도우미.
 *
 * <p>필터 조건은 모두 <b>지정하지 않으면 통과</b>다. 덕분에 컨트롤러에서 조건을 그냥 {@code &&} 로
 * 이어 붙이면 되고, 들어온 파라미터만 골라 검사하는 분기가 필요 없다.
 */
final class CacheQuery {

    private CacheQuery() {
    }

    /** 부분 일치. 대소문자를 가리지 않는다. {@code needle} 이 없으면 통과. */
    static boolean contains(String value, String needle) {
        if (needle == null || needle.isBlank()) {
            return true;
        }
        return value != null && value.toLowerCase().contains(needle.toLowerCase());
    }

    /** 문자열 완전 일치. 대소문자를 가리지 않는다. {@code expected} 가 없으면 통과. */
    static boolean matches(String value, String expected) {
        if (expected == null || expected.isBlank()) {
            return true;
        }
        return expected.equalsIgnoreCase(value);
    }

    /** 값 일치. {@code expected} 가 없으면 통과. */
    static boolean equalTo(Object value, Object expected) {
        return expected == null || expected.equals(value);
    }

    /** 하한 비교. {@code min} 이 없으면 통과. 값이 없으면 탈락. */
    static boolean atLeast(Number value, Number min) {
        if (min == null) {
            return true;
        }
        return value != null && value.doubleValue() >= min.doubleValue();
    }

    /** 맵에 담긴 항목의 문자열 필드에 대한 부분 일치. */
    static boolean containsAt(Map<String, Object> row, String field, String needle) {
        if (needle == null || needle.isBlank()) {
            return true;
        }
        Object value = row == null ? null : row.get(field);
        return value != null && value.toString().toLowerCase().contains(needle.toLowerCase());
    }

    /**
     * 필터를 적용한 뒤 잘라낸다.
     *
     * @param items  원본 목록
     * @param filter 남길 조건
     * @param limit  최대 개수. {@code null} 이면 제한 없음
     * @param offset 건너뛸 개수. {@code null} 이면 0
     */
    static <E> List<E> apply(List<E> items, Predicate<E> filter, Integer limit, Integer offset) {
        List<E> filtered = items.stream().filter(filter).toList();

        int from = offset == null ? 0 : Math.max(0, offset);
        if (from >= filtered.size()) {
            return List.of();
        }
        int to = limit == null ? filtered.size() : Math.min(filtered.size(), from + Math.max(0, limit));
        return filtered.subList(from, to);
    }

    /** 필터만 적용해 전체 개수를 센다. 페이징 전 {@code total} 계산용. */
    static <E> int countMatching(List<E> items, Predicate<E> filter) {
        return (int) items.stream().filter(filter).count();
    }
}
