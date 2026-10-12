package com.cvGateway.cvGateway.cybervision.token;

/** 토큰을 Cyber Vision 에 물어본 결과. */
public enum TokenValidation {

    /** Center 가 토큰을 받아들였다. */
    VALID,

    /** Center 가 401 로 거부했다. 토큰이 틀렸거나 만료·비활성 상태다. */
    REJECTED,

    /**
     * Center 에 물어보지 못했다. 연결 실패, 타임아웃, 401 이 아닌 오류 등.
     *
     * <p>토큰이 잘못됐다는 근거는 아니다. Center 가 잠시 내려가 있을 때 정상 토큰을 미리
     * 등록해 두려는 경우도 여기에 해당한다.
     */
    UNREACHABLE,

    /** 검증을 하지 않도록 설정돼 있어 확인하지 않았다. */
    SKIPPED
}
