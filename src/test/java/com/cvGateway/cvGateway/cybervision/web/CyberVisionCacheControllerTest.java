package com.cvGateway.cvGateway.cybervision.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cvGateway.cvGateway.cybervision.cache.CyberVisionStateCache;
import com.cvGateway.cvGateway.cybervision.cache.FreshnessRegistry;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionNotConfiguredException;
import com.cvGateway.cvGateway.cybervision.model.Group;
import com.cvGateway.cvGateway.cybervision.model.SensorOverview;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilityDetail;

/**
 * 컨트롤러의 필터·페이징과 응답 포맷을 검증한다.
 *
 * <p>캐시에 값을 직접 넣고 메서드를 호출하므로 Spring 컨텍스트도 Cyber Vision 서버도 필요 없다.
 */
class CyberVisionCacheControllerTest {

    private final CyberVisionStateCache cache = new CyberVisionStateCache(new FreshnessRegistry());
    private final CyberVisionConnectionStore connectionStore = new CyberVisionConnectionStore();
    private final CyberVisionCacheController controller =
            new CyberVisionCacheController(cache, connectionStore);

    @BeforeEach
    void injectConnection() {
        connectionStore.update(CyberVisionConnection.of(
                "1", "https://center.test", "sw-01", "5.5.0", "ics-test-token", java.time.Instant.now()));
    }

    @BeforeEach
    void seedSensors() {
        cache.sensors().set(List.of(
                sensor("sensor-alpha", "IC3000", "center-1"),
                sensor("sensor-beta", "IC3000", "center-2"),
                sensor("gateway-alpha", "IR1101", "center-1")));
    }

    @Test
    void 필터를_주지_않으면_전부_나온다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors(null, null, null, null, null, null);

        assertThat(response.available()).isTrue();
        assertThat(response.total()).isEqualTo(3);
        assertThat(response.count()).isEqualTo(3);
        assertThat(response.failing()).isFalse();
        assertThat(response.fetchedAt()).isNotNull();
        assertThat(response.ageSeconds()).isNotNull();
    }

    @Test
    void 이름은_부분_일치로_걸린다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors("alpha", null, null, null, null, null);

        assertThat(response.data())
                .extracting(SensorOverview::getName)
                .containsExactly("sensor-alpha", "gateway-alpha");
        assertThat(response.total()).isEqualTo(2);
    }

    @Test
    void 부분_일치는_대소문자를_가리지_않는다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors("ALPHA", null, null, null, null, null);

        assertThat(response.count()).isEqualTo(2);
    }

    @Test
    void 모델은_완전_일치라_부분_문자열로는_안_걸린다() {
        assertThat(controller.sensors(null, "IC3000", null, null, null, null).count())
                .isEqualTo(2);
        assertThat(controller.sensors(null, "IC", null, null, null, null).count())
                .as("완전 일치 필터에 부분 문자열을 주면 걸리지 않아야 한다")
                .isZero();
    }

    @Test
    void 여러_필터는_모두_만족해야_한다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors("alpha", "IC3000", "center-1", null, null, null);

        assertThat(response.data())
                .extracting(SensorOverview::getName)
                .containsExactly("sensor-alpha");
    }

    @Test
    void 페이징은_필터를_적용한_뒤에_동작한다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors("alpha", null, null, null, 1, 1);

        assertThat(response.total())
                .as("total 은 페이징 전, 필터를 통과한 전체 개수여야 한다")
                .isEqualTo(2);
        assertThat(response.count()).isEqualTo(1);
        assertThat(response.data())
                .extracting(SensorOverview::getName)
                .containsExactly("gateway-alpha");
    }

    @Test
    void offset_이_범위를_넘으면_빈_목록이_나온다() {
        CachedResponse<List<SensorOverview>> response =
                controller.sensors(null, null, null, null, null, 99);

        assertThat(response.data()).isEmpty();
        assertThat(response.total()).isEqualTo(3);
    }

    @Test
    void 한_번도_받아오지_못한_항목은_값_없음으로_응답한다() {
        CachedResponse<List<VulnerabilityDetail>> response =
                controller.vulnerabilities(null, null, null, null, null, null);

        assertThat(response.available())
                .as("조건에 맞는 항목이 없는 것과 아직 못 받아온 것은 구분돼야 한다")
                .isFalse();
        assertThat(response.data()).isNull();
        assertThat(response.total()).isNull();
    }

    @Test
    void 폴링이_실패해도_직전_값과_실패_사유를_함께_준다() {
        cache.sensors().fail("connection refused");

        CachedResponse<List<SensorOverview>> response =
                controller.sensors(null, null, null, null, null, null);

        assertThat(response.available()).isTrue();
        assertThat(response.count()).isEqualTo(3);
        assertThat(response.failing()).isTrue();
        assertThat(response.lastError()).isEqualTo("connection refused");
    }

    @Test
    void 숫자_하한_필터가_동작한다() {
        cache.vulnerabilities().set(List.of(
                vulnerability("CVE-2021-1", 9.8),
                vulnerability("CVE-2021-2", 5.0),
                vulnerability("CVE-2021-3", null)));

        CachedResponse<List<VulnerabilityDetail>> response =
                controller.vulnerabilities(null, null, null, 7.0, null, null);

        assertThat(response.data())
                .extracting(VulnerabilityDetail::getCve)
                .as("점수가 없는 항목은 하한 조건을 통과하지 못한다")
                .containsExactly("CVE-2021-1");
    }

    @Test
    void rootOnly_는_상위_그룹이_없는_항목만_남긴다() {
        cache.groups().set(List.of(
                group("zone-root", null, 3L),
                group("zone-child", "zone-root", 1L)));

        assertThat(controller.groups(null, null, null, null, true, null, null).data())
                .extracting(Group::getLabel)
                .containsExactly("zone-root");
        assertThat(controller.groups(null, null, 2L, null, null, null, null).data())
                .extracting(Group::getLabel)
                .as("중요도 하한 필터")
                .containsExactly("zone-root");
    }

    @Test
    void 중요도_완전일치_필터가_동작한다() {
        cache.groups().set(List.of(
                group("zone-root", null, 3L),
                group("zone-child", "zone-root", 1L)));

        assertThat(controller.groups(null, 3L, null, null, null, null, null).data())
                .extracting(Group::getLabel)
                .as("Long 필드를 Integer 로 받으면 equals 가 항상 거짓이 되어 아무것도 안 걸린다")
                .containsExactly("zone-root");
    }

    @Test
    void 접속_정보가_없으면_주입하라는_오류를_던진다() {
        connectionStore.clear();

        assertThatThrownBy(() -> controller.sensors(null, null, null, null, null, null))
                .as("캐시가 비어 있는 것과 접속 정보가 없는 것은 조치가 다르므로 구분돼야 한다")
                .isInstanceOf(CyberVisionNotConfiguredException.class)
                .hasMessageContaining("/api/cyber-vision/connection");
    }

    @Test
    void 신선도_현황은_접속_정보_없이도_볼_수_있다() {
        connectionStore.clear();

        assertThatCode(controller::freshness)
                .as("진단용 현황까지 막으면 왜 안 되는지 확인할 방법이 없어진다")
                .doesNotThrowAnyException();
    }

    @Test
    void 전체_신선도_현황을_돌려준다() {
        cache.sensors().fail("timeout");

        assertThat(controller.freshness()).containsKey("sensors");
        assertThat(controller.freshness().get("sensors").isFailing()).isTrue();
    }

    private static SensorOverview sensor(String name, String model, String centerId) {
        SensorOverview sensor = new SensorOverview();
        sensor.setName(name);
        sensor.setModel(model);
        sensor.setCenterId(centerId);
        return sensor;
    }

    private static VulnerabilityDetail vulnerability(String cve, Double cvss) {
        VulnerabilityDetail vulnerability = new VulnerabilityDetail();
        vulnerability.setCve(cve);
        vulnerability.setCVSS(cvss);
        return vulnerability;
    }

    private static Group group(String label, String parentId, Long criticalness) {
        Group group = new Group();
        group.setLabel(label);
        group.setParentId(parentId);
        group.setCriticalness(criticalness);
        return group;
    }
}
