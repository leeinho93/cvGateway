package com.cvGateway.cvGateway.cybervision.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cvGateway.cvGateway.cybervision.model.ActiveDiscoveryProfile;
import com.cvGateway.cvGateway.cybervision.model.AllCountResult;
import com.cvGateway.cvGateway.cybervision.model.AllCounters;
import com.cvGateway.cvGateway.cybervision.model.Baseline;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Device;
import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.Group;
import com.cvGateway.cvGateway.cybervision.model.LightFolder;
import com.cvGateway.cvGateway.cybervision.model.NetworkNodes;
import com.cvGateway.cvGateway.cybervision.model.Preset;
import com.cvGateway.cvGateway.cybervision.model.RiskScoreDevicesCountResponse;
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardCounts;
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardElement;
import com.cvGateway.cvGateway.cybervision.model.SensorOverview;
import com.cvGateway.cvGateway.cybervision.model.Version;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilitiyForList;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilityDetail;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventByCategory;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventsBySeverity;
import com.cvGateway.cvGateway.cybervision.model.WidgetProtocolDistributionResponse;
import com.cvGateway.cvGateway.cybervision.model.WidgetVulnerableDevicesResponse;

/**
 * Cyber Vision 에서 주기적으로 받아온 현재 상태.
 *
 * <p>항목마다 {@link StateSlot} 을 하나씩 두어 각자 자기 값을 들고 있고, 신선도만 공용
 * {@link FreshnessRegistry} 한곳에 모인다. 값과 메타데이터의 관리 주체를 나눈 구조다.
 *
 * <p>읽는 쪽은 항목별 메서드를 쓴다. 타입이 슬롯에 박혀 있어 캐스팅이 없다.
 * <pre>{@code
 * cache.sensors().get().ifPresent(list -> ...);
 * Duration age = cache.devices().freshness().age();
 * }</pre>
 *
 * <p>쓰기는 폴링 스케줄러만 한다. 값은 통째로 교체되며 부분 수정은 없다.
 */
@Component
public class CyberVisionStateCache {

    /** 스케줄러가 키만 알고 슬롯을 찾아갈 수 있도록 하는 색인. 값은 각 슬롯이 들고 있다. */
    private final Map<StateKey<?>, StateSlot<?>> index = new ConcurrentHashMap<>();

    private final FreshnessRegistry freshness;

    // ------------------------------------------------------------------ 운영 상태
    private final StateSlot<List<SensorOverview>> sensors;
    private final StateSlot<WidgetEventsBySeverity> eventsBySeverity;
    private final StateSlot<WidgetEventByCategory> eventsByCategory;

    // ------------------------------------------------------------------ 집계 지표
    private final StateSlot<AllCounters> dashboardCounts;
    private final StateSlot<RiskScoreDevicesCountResponse> riskScores;
    private final StateSlot<WidgetVulnerableDevicesResponse> vulnerableDevices;
    private final StateSlot<WidgetProtocolDistributionResponse> protocolDistribution;

    // ------------------------------------------------------------------ 목록
    private final StateSlot<Device> devices;
    private final StateSlot<List<VulnerabilityDetail>> vulnerabilities;
    private final StateSlot<List<ActiveDiscoveryProfile>> activeDiscoveryProfiles;
    private final StateSlot<List<DetailedTag>> tags;

    // ------------------------------------------------------------------ 구성 정보
    private final StateSlot<List<Group>> groups;
    private final StateSlot<List<Map<String, Object>>> customNetworks;
    private final StateSlot<List<Preset>> presets;
    private final StateSlot<Version> version;
    private final StateSlot<List<Baseline>> baselines;
    private final StateSlot<LightFolder> sensorFolders;

    // ------------------------------------------------------------------ 프리셋 단위
    private final StateSlot<Map<String, AllCountResult>> presetDashboardCounts;
    private final StateSlot<Map<String, List<VulnerabilitiyForList>>> presetVulnerabilities;
    private final StateSlot<Map<String, NetworkNodes>> presetNetworkNodes;
    private final StateSlot<Map<String, Flow>> presetTaglessFlows;
    private final StateSlot<Map<String, Map<String, SecurityInsightDashboardCounts>>> securityInsightCounts;
    private final StateSlot<Map<String, Map<String, SecurityInsightDashboardElement>>> securityInsightList;

    public CyberVisionStateCache(FreshnessRegistry freshness) {
        this.freshness = freshness;

        this.sensors = slotFor(StateKeys.SENSORS);
        this.eventsBySeverity = slotFor(StateKeys.EVENTS_BY_SEVERITY);
        this.eventsByCategory = slotFor(StateKeys.EVENTS_BY_CATEGORY);

        this.dashboardCounts = slotFor(StateKeys.DASHBOARD_COUNTS);
        this.riskScores = slotFor(StateKeys.RISK_SCORES);
        this.vulnerableDevices = slotFor(StateKeys.VULNERABLE_DEVICES);
        this.protocolDistribution = slotFor(StateKeys.PROTOCOL_DISTRIBUTION);

        this.devices = slotFor(StateKeys.DEVICES);
        this.vulnerabilities = slotFor(StateKeys.VULNERABILITIES);
        this.activeDiscoveryProfiles = slotFor(StateKeys.ACTIVE_DISCOVERY_PROFILES);
        this.tags = slotFor(StateKeys.TAGS);

        this.groups = slotFor(StateKeys.GROUPS);
        this.customNetworks = slotFor(StateKeys.CUSTOM_NETWORKS);
        this.presets = slotFor(StateKeys.PRESETS);
        this.version = slotFor(StateKeys.VERSION);
        this.baselines = slotFor(StateKeys.BASELINES);
        this.sensorFolders = slotFor(StateKeys.SENSOR_FOLDERS);

        this.presetDashboardCounts = slotFor(StateKeys.PRESET_DASHBOARD_COUNTS);
        this.presetVulnerabilities = slotFor(StateKeys.PRESET_VULNERABILITIES);
        this.presetNetworkNodes = slotFor(StateKeys.PRESET_NETWORK_NODES);
        this.presetTaglessFlows = slotFor(StateKeys.PRESET_TAGLESS_FLOWS);
        this.securityInsightCounts = slotFor(StateKeys.SECURITY_INSIGHT_COUNTS);
        this.securityInsightList = slotFor(StateKeys.SECURITY_INSIGHT_LIST);
    }

    // ------------------------------------------------------------------ 항목별 접근

    /** 센서 목록과 각 센서의 연결 상태. */
    public StateSlot<List<SensorOverview>> sensors() {
        return sensors;
    }

    /** 심각도별 최근 이벤트. */
    public StateSlot<WidgetEventsBySeverity> eventsBySeverity() {
        return eventsBySeverity;
    }

    /** 카테고리별 최근 이벤트. */
    public StateSlot<WidgetEventByCategory> eventsByCategory() {
        return eventsByCategory;
    }

    /** 홈 대시보드의 객체 종류별 개수. */
    public StateSlot<AllCounters> dashboardCounts() {
        return dashboardCounts;
    }

    /** 리스크 스코어 구간별 디바이스 수. */
    public StateSlot<RiskScoreDevicesCountResponse> riskScores() {
        return riskScores;
    }

    /** 취약점 심각도별 디바이스 수. */
    public StateSlot<WidgetVulnerableDevicesResponse> vulnerableDevices() {
        return vulnerableDevices;
    }

    /** 프로토콜별 액티비티 분포. */
    public StateSlot<WidgetProtocolDistributionResponse> protocolDistribution() {
        return protocolDistribution;
    }

    /** 자산(디바이스) 인벤토리. */
    public StateSlot<Device> devices() {
        return devices;
    }

    /** 탐지된 취약점 목록. */
    public StateSlot<List<VulnerabilityDetail>> vulnerabilities() {
        return vulnerabilities;
    }

    /** 능동 탐색 프로필과 각 프로필의 예약 상태. */
    public StateSlot<List<ActiveDiscoveryProfile>> activeDiscoveryProfiles() {
        return activeDiscoveryProfiles;
    }

    /** 태그 목록. */
    public StateSlot<List<DetailedTag>> tags() {
        return tags;
    }

    /** 그룹(존) 트리. */
    public StateSlot<List<Group>> groups() {
        return groups;
    }

    /** 사용자 정의 네트워크 설정. */
    public StateSlot<List<Map<String, Object>>> customNetworks() {
        return customNetworks;
    }

    /** 프리셋 목록. 프리셋 단위 조회를 하려면 여기서 ID 를 얻는다. */
    public StateSlot<List<Preset>> presets() {
        return presets;
    }

    /** Center 버전. */
    public StateSlot<Version> version() {
        return version;
    }

    /** 베이스라인 목록. */
    public StateSlot<List<Baseline>> baselines() {
        return baselines;
    }

    /** 센서 폴더 트리. */
    public StateSlot<LightFolder> sensorFolders() {
        return sensorFolders;
    }

    // ------------------------------------------------------------------ 프리셋 단위

    /** 프리셋별 대시보드 집계. 키는 프리셋 ID. */
    public StateSlot<Map<String, AllCountResult>> presetDashboardCounts() {
        return presetDashboardCounts;
    }

    /** 프리셋별 취약점 목록. 키는 프리셋 ID. */
    public StateSlot<Map<String, List<VulnerabilitiyForList>>> presetVulnerabilities() {
        return presetVulnerabilities;
    }

    /** 프리셋별 네트워크 노드 목록. 키는 프리셋 ID. */
    public StateSlot<Map<String, NetworkNodes>> presetNetworkNodes() {
        return presetNetworkNodes;
    }

    /** 프리셋별 태그 없는(미분류) 플로우. 키는 프리셋 ID. */
    public StateSlot<Map<String, Flow>> presetTaglessFlows() {
        return presetTaglessFlows;
    }

    /** 프리셋·프로토콜별 보안 인사이트 집계. 키는 프리셋 ID, 그 다음 프로토콜 유형. */
    public StateSlot<Map<String, Map<String, SecurityInsightDashboardCounts>>> securityInsightCounts() {
        return securityInsightCounts;
    }

    /** 프리셋·프로토콜별 보안 인사이트 목록. 키는 프리셋 ID, 그 다음 프로토콜 유형. */
    public StateSlot<Map<String, Map<String, SecurityInsightDashboardElement>>> securityInsightList() {
        return securityInsightList;
    }

    // ------------------------------------------------------------------ 공통

    /** 전체 항목의 갱신 시각과 실패 여부. 헬스체크·운영 점검용. */
    public Map<String, Freshness> freshness() {
        return freshness.all();
    }

    /**
     * 키로 슬롯을 찾는다. 폴링 스케줄러가 등록된 대상을 순회하며 채울 때 쓴다.
     *
     * <p>일반 조회에는 항목별 메서드를 쓰는 편이 낫다. 키를 거칠 이유가 없다.
     *
     * @throws IllegalArgumentException 등록되지 않은 키인 경우
     */
    @SuppressWarnings("unchecked")
    public <T> StateSlot<T> slot(StateKey<T> key) {
        StateSlot<?> slot = index.get(key);
        if (slot == null) {
            throw new IllegalArgumentException("캐시에 등록되지 않은 키입니다: " + key.name());
        }
        // 슬롯은 slotFor 에서 같은 키의 타입으로만 만들어지므로 T 가 보장된다.
        return (StateSlot<T>) slot;
    }

    private <T> StateSlot<T> slotFor(StateKey<T> key) {
        StateSlot<T> slot = new StateSlot<>(key, freshness);
        index.put(key, slot);
        return slot;
    }
}
