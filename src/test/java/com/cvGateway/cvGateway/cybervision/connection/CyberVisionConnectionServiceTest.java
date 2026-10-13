// ===== DB 미사용 모드: 파일 전체 주석 처리 =====
// 이 테스트는 "DB 먼저, 메모리 나중" 규칙을 검증한다. DB 코드가 내려가 리포지토리와
// 엔티티가 없으므로 그대로는 컴파일되지 않는다. DB 를 되살릴 때 이 파일도 함께 복구한다.
//
//package com.cvGateway.cvGateway.cybervision.connection;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatCode;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.Instant;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
//import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
//import com.cvGateway.cvGateway.cybervision.config.CyberVisionProperties;
//import com.cvGateway.cvGateway.cybervision.token.CyberVisionTokenValidator;
//import com.cvGateway.cvGateway.cybervision.token.InvalidTokenException;
//import com.cvGateway.cvGateway.cybervision.token.TokenValidation;
//
///**
// * 접속 정보의 DB 보관과 메모리 반영이 맞물려 도는지 검증한다.
// *
// * <p>검증 대상은 SQL 이 아니라 "DB 와 메모리 중 어느 쪽이 먼저 바뀌고, 실패하면 어떻게 되는가"
// * 하는 서비스의 판단이다. 보관소는 실제 구현을 쓴다 — 단순한 메모리 홀더라 가짜로 바꿀 이유가 없다.
// */
//@ExtendWith(MockitoExtension.class)
//class CyberVisionConnectionServiceTest {
//
//    @Mock
//    private CyberVisionConnectionRepository repository;
//
//    @Spy
//    private CyberVisionConnectionStore connectionStore = new CyberVisionConnectionStore();
//
//    @Mock
//    private CyberVisionTokenValidator validator;
//
//    @Spy
//    private CyberVisionProperties properties = new CyberVisionProperties();
//
//    @InjectMocks
//    private CyberVisionConnectionService service;
//
//    private void validationPasses() {
//        when(validator.validate(anyString(), anyString())).thenReturn(TokenValidation.VALID);
//    }
//
//    private static CyberVisionConnectionEntity entity(String id, String url, String token) {
//        return new CyberVisionConnectionEntity(CyberVisionConnection.of(
//                id, url, "sw-01", "5.5.0", token, Instant.now()));
//    }
//
//    // ------------------------------------------------------------------ 기동 시 적재
//
//    @Test
//    void 기동_시_가장_최근_접속_정보를_메모리에_올린다() {
//        when(repository.findFirstByOrderByUpdatedAtDesc())
//                .thenReturn(Optional.of(entity("7", "https://center-a", "ics-db")));
//
//        service.loadFromDatabase();
//
//        CyberVisionConnection loaded = connectionStore.get().orElseThrow();
//        assertThat(loaded.id()).isEqualTo("7");
//        assertThat(loaded.centerUrl()).isEqualTo("https://center-a");
//        assertThat(loaded.token()).isEqualTo("ics-db");
//        assertThat(loaded.switchName()).isEqualTo("sw-01");
//        assertThat(loaded.version()).isEqualTo("5.5.0");
//    }
//
//    @Test
//    void 기동_시_DB_가_비어_있으면_설정되지_않은_상태로_시작한다() {
//        when(repository.findFirstByOrderByUpdatedAtDesc()).thenReturn(Optional.empty());
//
//        service.loadFromDatabase();
//
//        assertThat(connectionStore.isConfigured()).isFalse();
//    }
//
//    @Test
//    void DB_조회가_실패해도_기동을_막지_않는다() {
//        when(repository.findFirstByOrderByUpdatedAtDesc())
//                .thenThrow(new IllegalStateException("DB 연결 실패"));
//
//        assertThatCode(service::loadFromDatabase)
//                .as("여기서 예외를 던지면 정보를 주입할 API 조차 안 열려 복구 수단이 사라진다")
//                .doesNotThrowAnyException();
//        assertThat(connectionStore.isConfigured()).isFalse();
//    }
//
//    // ------------------------------------------------------------------ 주입
//
//    @Test
//    void 처음_주입하면_새_행을_저장하고_메모리에_반영한다() {
//        validationPasses();
//        when(repository.findById("1")).thenReturn(Optional.empty());
//
//        service.update("1", "https://center-a", "sw-01", "5.5.0", "ics-new");
//
//        ArgumentCaptor<CyberVisionConnectionEntity> saved =
//                ArgumentCaptor.forClass(CyberVisionConnectionEntity.class);
//        verify(repository).saveAndFlush(saved.capture());
//        assertThat(saved.getValue().getId()).isEqualTo("1");
//        assertThat(saved.getValue().getIp()).isEqualTo("https://center-a");
//        assertThat(saved.getValue().getName()).isEqualTo("sw-01");
//        assertThat(saved.getValue().getVersion()).isEqualTo("5.5.0");
//        assertThat(connectionStore.token()).contains("ics-new");
//    }
//
//    @Test
//    void 같은_id_로_다시_주입하면_행을_교체한다() {
//        validationPasses();
//        CyberVisionConnectionEntity existing = entity("1", "https://old", "ics-old");
//        when(repository.findById("1")).thenReturn(Optional.of(existing));
//
//        service.update("1", "https://new", "sw-02", "5.5.1", "ics-new");
//
//        ArgumentCaptor<CyberVisionConnectionEntity> saved =
//                ArgumentCaptor.forClass(CyberVisionConnectionEntity.class);
//        verify(repository).saveAndFlush(saved.capture());
//        assertThat(saved.getValue())
//                .as("같은 id 로 행이 늘어나면 어느 것이 유효한지 알 수 없게 된다")
//                .isSameAs(existing);
//        assertThat(existing.getIp()).isEqualTo("https://new");
//        assertThat(existing.getTokenValue()).isEqualTo("ics-new");
//        assertThat(connectionStore.url()).contains("https://new");
//    }
//
//    @Test
//    void 주소_뒤_슬래시는_제거되고_저장된다() {
//        validationPasses();
//        when(repository.findById("1")).thenReturn(Optional.empty());
//
//        service.update("1", "https://center-a/", null, null, "ics-token");
//
//        assertThat(connectionStore.url())
//                .as("경로를 이어 붙일 때 이중 슬래시가 생기지 않아야 한다")
//                .contains("https://center-a");
//    }
//
//    @Test
//    void switchName_과_version_은_없어도_된다() {
//        validationPasses();
//        when(repository.findById("1")).thenReturn(Optional.empty());
//
//        service.update("1", "https://center-a", null, null, "ics-token");
//
//        CyberVisionConnection stored = connectionStore.get().orElseThrow();
//        assertThat(stored.switchName()).isNull();
//        assertThat(stored.version()).isNull();
//    }
//
//    @Test
//    void 필수_값이_없으면_DB_조회도_하지_않고_거부한다() {
//        assertThatThrownBy(() -> service.update(null, "https://a", null, null, "t"))
//                .isInstanceOf(IllegalArgumentException.class);
//        assertThatThrownBy(() -> service.update("1", null, null, null, "t"))
//                .isInstanceOf(IllegalArgumentException.class);
//        assertThatThrownBy(() -> service.update("1", "https://a", null, null, "  "))
//                .isInstanceOf(IllegalArgumentException.class);
//
//        verify(repository, never()).saveAndFlush(any());
//        assertThat(connectionStore.isConfigured()).isFalse();
//    }
//
//    @Test
//    void 스킴이_없는_주소는_거부한다() {
//        assertThatThrownBy(() -> service.update("1", "192.168.0.10", null, null, "ics-token"))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("http");
//
//        verify(repository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    void DB_저장이_실패하면_메모리는_바뀌지_않는다() {
//        validationPasses();
//        connectionStore.update(CyberVisionConnection.of(
//                "1", "https://keep", "sw", "5.5", "ics-keep", Instant.now()));
//        when(repository.findById("2")).thenReturn(Optional.empty());
//        doThrow(new IllegalStateException("DB 저장 실패")).when(repository).saveAndFlush(any());
//
//        assertThatThrownBy(() -> service.update("2", "https://doomed", null, null, "ics-doomed"))
//                .isInstanceOf(IllegalStateException.class);
//
//        assertThat(connectionStore.token())
//                .as("DB 는 실패했는데 메모리만 바뀌면 재기동 시 조용히 예전 값으로 돌아간다")
//                .contains("ics-keep");
//    }
//
//    // ------------------------------------------------------------------ 등록 전 검증
//
//    @Test
//    void Center_가_거부한_토큰은_저장하지_않는다() {
//        connectionStore.update(CyberVisionConnection.of(
//                "1", "https://keep", "sw", "5.5", "ics-keep", Instant.now()));
//        when(validator.validate("https://center-a", "ics-bad")).thenReturn(TokenValidation.REJECTED);
//
//        assertThatThrownBy(() -> service.update("2", "https://center-a", null, null, "ics-bad"))
//                .isInstanceOf(InvalidTokenException.class);
//
//        verify(repository, never()).saveAndFlush(any());
//        assertThat(connectionStore.token())
//                .as("거부된 값이 기존 접속 정보를 덮어쓰면 멀쩡하던 연동까지 끊긴다")
//                .contains("ics-keep");
//    }
//
//    @Test
//    void Center_에_닿지_못하면_경고만_하고_저장한다() {
//        when(validator.validate("https://center-a", "ics-unverified"))
//                .thenReturn(TokenValidation.UNREACHABLE);
//        when(repository.findById("1")).thenReturn(Optional.empty());
//
//        TokenValidation result =
//                service.update("1", "https://center-a", null, null, "ics-unverified");
//
//        assertThat(result).isEqualTo(TokenValidation.UNREACHABLE);
//        assertThat(connectionStore.token())
//                .as("Center 점검 중에 새 정보를 미리 넣어 두는 경우를 막으면 안 된다")
//                .contains("ics-unverified");
//    }
//
//    @Test
//    void 검증을_끄면_Center_에_묻지_않는다() {
//        properties.setValidateTokenOnUpdate(false);
//        when(repository.findById("1")).thenReturn(Optional.empty());
//
//        TokenValidation result = service.update("1", "https://center-a", null, null, "ics-no-check");
//
//        assertThat(result).isEqualTo(TokenValidation.SKIPPED);
//        verify(validator, never()).validate(any(), any());
//    }
//
//    // ------------------------------------------------------------------ 삭제
//
//    @Test
//    void 사용_중인_접속_정보를_지우면_메모리도_비운다() {
//        CyberVisionConnectionEntity existing = entity("1", "https://center-a", "ics-bye");
//        when(repository.findById("1")).thenReturn(Optional.of(existing));
//        connectionStore.update(existing.toConnection());
//
//        service.clear("1");
//
//        verify(repository).delete(existing);
//        assertThat(connectionStore.isConfigured()).isFalse();
//    }
//
//    @Test
//    void 사용하지_않는_id_를_지워도_현재_연결은_유지된다() {
//        CyberVisionConnectionEntity other = entity("2", "https://center-b", "ics-other");
//        when(repository.findById("2")).thenReturn(Optional.of(other));
//        connectionStore.update(CyberVisionConnection.of(
//                "1", "https://center-a", "sw", "5.5", "ics-active", Instant.now()));
//
//        service.clear("2");
//
//        verify(repository).delete(other);
//        assertThat(connectionStore.token())
//                .as("지금 쓰고 있는 연결을 남의 id 삭제로 끊을 이유가 없다")
//                .contains("ics-active");
//    }
//
//    @Test
//    void 없는_id_를_지워도_예외가_나지_않는다() {
//        when(repository.findById("99")).thenReturn(Optional.empty());
//
//        assertThatCode(() -> service.clear("99")).doesNotThrowAnyException();
//
//        verify(repository, never()).delete(any());
//    }
//}
