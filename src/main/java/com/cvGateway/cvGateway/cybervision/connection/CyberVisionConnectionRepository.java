// ===== DB 미사용 모드: 파일 전체 주석 처리 =====
// DB 없이 기동하기 위해 JPA 리포지토리를 내렸다. build.gradle 의 JPA 의존성이
// 빠져 있어 주석을 풀면 컴파일되지 않는다. 되살릴 때는 의존성과 application.yaml 의
// datasource / jpa 블록, CyberVisionConnectionService 의 DB 코드를 함께 복구한다.
//
//package com.cvGateway.cvGateway.cybervision.connection;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
///** {@link CyberVisionConnectionEntity} 저장소. */
//public interface CyberVisionConnectionRepository extends JpaRepository<CyberVisionConnectionEntity, String> {
//
//    /**
//     * 가장 최근에 주입된 접속 정보.
//     *
//     * <p>기동 시 무엇을 쓸지 정하는 데 사용한다. DB 에 여러 {@code id} 가 있어도 게이트웨이가
//     * 동시에 바라보는 Center 는 하나이므로, 마지막에 등록된 것을 현재 설정으로 본다.
//     */
//    Optional<CyberVisionConnectionEntity> findFirstByOrderByUpdatedAtDesc();
//}
