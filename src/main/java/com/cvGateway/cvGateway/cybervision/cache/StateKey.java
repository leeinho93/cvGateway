package com.cvGateway.cvGateway.cybervision.cache;

/**
 * 캐시 항목 하나를 가리키는 타입 붙은 키.
 *
 * <p>캐시는 하나지만 항목마다 담기는 구조체가 다르다. 키에 제네릭을 물려두면
 * {@link CyberVisionStateCache#get} 이 캐스팅 없이 올바른 타입을 돌려준다.
 *
 * <p>키 상수는 {@link StateKeys} 에 모아 둔다.
 *
 * @param <T> 이 키에 저장되는 구조체 타입
 */
public final class StateKey<T> {

    private final String name;

    private StateKey(String name) {
        this.name = name;
    }

    /**
     * 키를 만든다.
     *
     * <p>타입 인자는 대입 대상에서 추론되므로 상수 선언 시 왼쪽 타입을 반드시 명시한다.
     * 예: {@code StateKey<List<Group>> GROUPS = StateKey.of("groups");}
     */
    public static <T> StateKey<T> of(String name) {
        return new StateKey<>(name);
    }

    /** 로그와 진단 출력에 쓰는 이름. */
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
