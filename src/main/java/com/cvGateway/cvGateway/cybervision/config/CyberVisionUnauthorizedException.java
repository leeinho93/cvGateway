package com.cvGateway.cvGateway.cybervision.config;

/**
 * Cyber Vision 이 401 을 돌려줬을 때 발생한다. 즉 {@code x-token-id} 가 거부됐다.
 *
 * <p>다른 오류와 구분해 두는 이유는 성격이 다르기 때문이다. 이 오류는 재시도해도 절대 풀리지 않고,
 * 모든 오퍼레이션이 동시에 같은 증상을 낸다. 사람이 Center UI 에서 토큰을 다시 발급하거나
 * 활성화해 주기 전에는 복구되지 않는다.
 *
 * <p>원인은 보통 셋 중 하나다.
 * <ul>
 *   <li>토큰 만료 — Cyber Vision 은 토큰 자동 갱신 API 를 제공하지 않는다.
 *       만료되면 관리 UI 에서 새로 발급받아 설정을 교체해야 한다.</li>
 *   <li>관리 UI 에서 토큰이 비활성화됨</li>
 *   <li>설정된 토큰 값 자체가 잘못됨</li>
 * </ul>
 *
 * <p>쓰기 오퍼레이션만 실패한다면 토큰이 아니라 권한 문제일 수 있다.
 * 기본 역할 중 API 쓰기 권한을 가진 것은 Admin 뿐이다.
 */
public class CyberVisionUnauthorizedException extends CyberVisionApiException {

    public CyberVisionUnauthorizedException(String operation, int status, String body) {
        super(operation, status, body);
    }
}
