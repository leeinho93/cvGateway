package com.cvGateway.cvGateway.cybervision.connection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/** {@link CyberVisionConnectionEntity} 저장소. */
public interface CyberVisionConnectionRepository extends JpaRepository<CyberVisionConnectionEntity, String> {

    /**
     * 가장 최근에 주입된 접속 정보.
     *
     * <p>기동 시 무엇을 쓸지 정하는 데 사용한다. DB 에 여러 {@code id} 가 있어도 게이트웨이가
     * 동시에 바라보는 Center 는 하나이므로, 마지막에 등록된 것을 현재 설정으로 본다.
     */
    Optional<CyberVisionConnectionEntity> findFirstByOrderByUpdatedAtDesc();
}
