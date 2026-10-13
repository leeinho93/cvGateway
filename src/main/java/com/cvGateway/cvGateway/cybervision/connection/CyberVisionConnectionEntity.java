// ===== DB 미사용 모드: 파일 전체 주석 처리 =====
// DB 없이 기동하기 위해 JPA 엔티티를 내렸다. build.gradle 의 JPA 의존성이
// 빠져 있어 주석을 풀면 컴파일되지 않는다. 되살릴 때는 의존성과 application.yaml 의
// datasource / jpa 블록, CyberVisionConnectionService 의 DB 코드를 함께 복구한다.
//
//package com.cvGateway.cvGateway.cybervision.connection;
//
//import java.time.Instant;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
///**
// * DB 에 보관하는 Cyber Vision 접속 정보.
// *
// * <p>외부 시스템이 API 로 주입하지만, 게이트웨이를 재기동해도 살아 있어야 하므로 DB 에도 남긴다.
// * 기동 시 {@link CyberVisionConnectionService} 가 이 값을 읽어 메모리 보관소를 채운다.
// *
// * <p>{@code id} 는 외부 시스템이 부여하는 값이며 그대로 기본 키가 된다. 같은 {@code id} 로 다시
// * 주입하면 행이 교체되고, 다른 {@code id} 로 주입하면 행이 하나 늘어난다.
// */
//@Entity
//@Table(name = "cv_connection")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class CyberVisionConnectionEntity {
//
//    /**
//     * 외부 시스템이 부여한 식별자.
//     *
//     * <p>숫자를 보내도 문자열로 받아 둔다. 식별자 형식이 나중에 바뀌어도 스키마를 건드리지 않기 위함이다.
//     */
//    @Id
//    @Column(name = "id", length = 100, nullable = false)
//    private String id;
//
//    /**
//     * Center 베이스 URL. 스킴 포함, 뒤 슬래시 없음. 예: {@code https://192.168.0.10}
//     *
//     * <p>주소를 설정 파일이 아니라 여기에 두는 이유는 운영 중 Center 를 바꿀 때 재배포 없이
//     * 교체할 수 있게 하기 위함이다.
//     */
//    @Column(name = "ip", length = 255, nullable = false)
//    private String ip;
//
//    /** 대상 스위치 이름. */
//    @Column(name = "switch_name", length = 255)
//    private String name;
//
//    /** 버전 문자열. */
//    @Column(name = "version", length = 50)
//    private String version;
//
//    /**
//     * 토큰 원문.
//     *
//     * <p>암호화하지 않고 그대로 저장한다. 이 테이블의 조회 권한이 곧 Cyber Vision 접근 권한이 되므로
//     * 필요한 계정에만 권한을 주어야 한다.
//     */
//    @Column(name = "token_value", length = 512, nullable = false)
//    private String tokenValue;
//
//    /** 마지막으로 주입된 시각. */
//    @Column(name = "updated_at", nullable = false)
//    private Instant updatedAt;
//
//    public CyberVisionConnectionEntity(CyberVisionConnection connection) {
//        this.id = connection.id();
//        applyFrom(connection);
//    }
//
//    /**
//     * 접속 정보를 통째로 덮어쓴다. {@code id} 는 키이므로 바뀌지 않는다.
//     *
//     * <p>주소와 토큰을 함께 바꾼다. Center 가 바뀌면 토큰도 그 Center 에서 발급한 것이어야 하므로
//     * 한쪽만 갱신할 수 있게 두지 않는다.
//     */
//    public void applyFrom(CyberVisionConnection connection) {
//        this.ip = connection.centerUrl();
//        this.name = connection.switchName();
//        this.version = connection.version();
//        this.tokenValue = connection.token();
//        this.updatedAt = connection.updatedAt();
//    }
//
//    /** 메모리 보관소에 담을 형태로 바꾼다. */
//    public CyberVisionConnection toConnection() {
//        return new CyberVisionConnection(id, ip, name, version, tokenValue, updatedAt);
//    }
//}
