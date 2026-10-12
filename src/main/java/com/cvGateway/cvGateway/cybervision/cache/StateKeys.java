package com.cvGateway.cvGateway.cybervision.cache;

import java.util.List;
import java.util.Map;

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
 * 캐시에 담기는 항목 목록.
 *
 * <p>여기 있는 것은 전부 <b>현재 상태</b>를 나타내는 데이터다. 플로우·액티비티 같은 이력성 데이터는
 * 주기적으로 받아 봐야 같은 과거를 다시 받는 셈이라 캐시에 두지 않고, 필요할 때 기간을 지정해
 * 직접 조회한다. 개별 자산 상세({@code /devices/{id}} 등)도 화면에서 선택했을 때만 필요하므로
 * 폴링 대상이 아니다.
 *
 * <p>새 항목을 추가하려면 여기에 키를 선언하고
 * {@code com.cvGateway.cvGateway.cybervision.polling.PollingTaskConfig} 에 조회 방법을 등록한다.
 */
public final class StateKeys {

    private StateKeys() {
    }

    // ------------------------------------------------------------------ 운영 상태

    /** 센서 목록과 각 센서의 연결 상태. */
    public static final StateKey<List<SensorOverview>> SENSORS = StateKey.of("sensors");

    /** 심각도별 최근 이벤트. */
    public static final StateKey<WidgetEventsBySeverity> EVENTS_BY_SEVERITY = StateKey.of("eventsBySeverity");

    /** 카테고리별 최근 이벤트. */
    public static final StateKey<WidgetEventByCategory> EVENTS_BY_CATEGORY = StateKey.of("eventsByCategory");

    // ------------------------------------------------------------------ 집계 지표

    /** 홈 대시보드의 객체 종류별 개수. */
    public static final StateKey<AllCounters> DASHBOARD_COUNTS = StateKey.of("dashboardCounts");

    /** 리스크 스코어 구간별 디바이스 수. */
    public static final StateKey<RiskScoreDevicesCountResponse> RISK_SCORES = StateKey.of("riskScores");

    /** 취약점 심각도별 디바이스 수. */
    public static final StateKey<WidgetVulnerableDevicesResponse> VULNERABLE_DEVICES = StateKey.of("vulnerableDevices");

    /** 프로토콜별 액티비티 분포. */
    public static final StateKey<WidgetProtocolDistributionResponse> PROTOCOL_DISTRIBUTION =
            StateKey.of("protocolDistribution");

    // ------------------------------------------------------------------ 목록

    /** 자산(디바이스) 인벤토리. */
    public static final StateKey<Device> DEVICES = StateKey.of("devices");

    /** 탐지된 취약점 목록. */
    public static final StateKey<List<VulnerabilityDetail>> VULNERABILITIES = StateKey.of("vulnerabilities");

    /** 능동 탐색 프로필과 각 프로필의 예약 상태. */
    public static final StateKey<List<ActiveDiscoveryProfile>> ACTIVE_DISCOVERY_PROFILES =
            StateKey.of("activeDiscoveryProfiles");

    /** 태그 목록. */
    public static final StateKey<List<DetailedTag>> TAGS = StateKey.of("tags");

    // ------------------------------------------------------------------ 구성 정보

    /** 그룹(존) 트리. */
    public static final StateKey<List<Group>> GROUPS = StateKey.of("groups");

    /** 사용자 정의 네트워크 설정. */
    public static final StateKey<List<Map<String, Object>>> CUSTOM_NETWORKS = StateKey.of("customNetworks");

    /** 프리셋 목록. 프리셋 단위 조회를 하려면 여기서 ID 를 얻는다. */
    public static final StateKey<List<Preset>> PRESETS = StateKey.of("presets");

    /** Center 버전. */
    public static final StateKey<Version> VERSION = StateKey.of("version");

    /** 베이스라인 목록. 정상 상태 기준선의 현재 목록이다. */
    public static final StateKey<List<Baseline>> BASELINES = StateKey.of("baselines");

    /** 센서 폴더 트리. */
    public static final StateKey<LightFolder> SENSOR_FOLDERS = StateKey.of("sensorFolders");

    // ------------------------------------------------------------------ 프리셋 단위

    /*
     * Cyber Vision 의 대시보드 계열 상당수가 preset_id 를 요구한다. 프리셋마다 캐시 항목을 따로
     * 만들면 키가 동적으로 늘어나 정적 선언이 불가능해지므로, 프리셋 ID 를 키로 하는 맵 하나에 담는다.
     * 어느 프리셋을 받아올지는 cyber-vision.polling.preset-ids 로 지정한다.
     */

    /** 프리셋별 대시보드 집계. {@code 프리셋 ID -> 집계}. */
    public static final StateKey<Map<String, AllCountResult>> PRESET_DASHBOARD_COUNTS =
            StateKey.of("presetDashboardCounts");

    /** 프리셋별 취약점 목록. {@code 프리셋 ID -> 취약점 목록}. */
    public static final StateKey<Map<String, List<VulnerabilitiyForList>>> PRESET_VULNERABILITIES =
            StateKey.of("presetVulnerabilities");

    /** 프리셋별 네트워크 노드(디바이스·컴포넌트) 목록. {@code 프리셋 ID -> 노드}. */
    public static final StateKey<Map<String, NetworkNodes>> PRESET_NETWORK_NODES =
            StateKey.of("presetNetworkNodes");

    /**
     * 프리셋별 태그 없는 플로우. {@code 프리셋 ID -> 플로우}.
     *
     * <p>어떤 태그도 붙지 않은 통신, 즉 분류되지 않은 트래픽이다. 보안 화면에서 조사 대상을
     * 찾는 출발점이 된다.
     */
    public static final StateKey<Map<String, Flow>> PRESET_TAGLESS_FLOWS =
            StateKey.of("presetTaglessFlows");

    /**
     * 프리셋·프로토콜별 보안 인사이트 집계. {@code 프리셋 ID -> 프로토콜 유형 -> 집계}.
     *
     * <p>프로토콜 유형은 {@code dns}, {@code http}, {@code smb} 중 설정된 것들이다.
     */
    public static final StateKey<Map<String, Map<String, SecurityInsightDashboardCounts>>>
            SECURITY_INSIGHT_COUNTS = StateKey.of("securityInsightCounts");

    /** 프리셋·프로토콜별 보안 인사이트 목록. {@code 프리셋 ID -> 프로토콜 유형 -> 항목}. */
    public static final StateKey<Map<String, Map<String, SecurityInsightDashboardElement>>>
            SECURITY_INSIGHT_LIST = StateKey.of("securityInsightList");
}
