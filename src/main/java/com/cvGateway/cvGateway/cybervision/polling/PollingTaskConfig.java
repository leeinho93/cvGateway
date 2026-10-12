package com.cvGateway.cvGateway.cybervision.polling;

import static com.cvGateway.cvGateway.cybervision.polling.RefreshTier.FAST;
import static com.cvGateway.cvGateway.cybervision.polling.RefreshTier.NORMAL;
import static com.cvGateway.cvGateway.cybervision.polling.RefreshTier.RARE;
import static com.cvGateway.cvGateway.cybervision.polling.RefreshTier.SLOW;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cvGateway.cvGateway.cybervision.api.ActiveDiscoveryClient;
import com.cvGateway.cvGateway.cybervision.api.BaselinesClient;
import com.cvGateway.cvGateway.cybervision.api.SensorExplorerClient;
import com.cvGateway.cvGateway.cybervision.api.CustomNetworkClient;
import com.cvGateway.cvGateway.cybervision.api.DashboardClient;
import com.cvGateway.cvGateway.cybervision.api.DevicesClient;
import com.cvGateway.cvGateway.cybervision.api.GroupsClient;
import com.cvGateway.cvGateway.cybervision.api.MiscClient;
import com.cvGateway.cvGateway.cybervision.api.PresetsClient;
import com.cvGateway.cvGateway.cybervision.api.SensorsClient;
import com.cvGateway.cvGateway.cybervision.api.TagsClient;
import com.cvGateway.cvGateway.cybervision.api.VulnerabilitiesClient;
import com.cvGateway.cvGateway.cybervision.cache.StateKeys;

import lombok.extern.slf4j.Slf4j;

/**
 * 어떤 캐시 항목을 어떤 등급으로 채울지 등록한다.
 *
 * <p>여기 있는 조회는 전부 <b>필수 파라미터가 없는</b> 전역 엔드포인트다. 프리셋 단위 조회는
 * {@code preset_id} 가 필요한데 그 값은 {@link StateKeys#PRESETS} 를 받아 봐야 알 수 있으므로
 * 이 단계에서는 넣지 않는다.
 *
 * <p>항목을 늘리려면 {@link StateKeys} 에 키를 선언하고 여기에 한 줄 추가하면 된다.
 * 스케줄러는 손댈 필요가 없다.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(PollingProperties.class)
public class PollingTaskConfig {

    /**
     * 폴링 대상 전체.
     *
     * <p>{@code null} 인자는 전부 선택 파라미터다. 지정하지 않으면 Center 기본값이 적용된다.
     */
    @Bean
    public List<PollingTask<?>> cyberVisionPollingTasks(
            PollingProperties properties,
            SensorsClient sensors,
            DashboardClient dashboard,
            DevicesClient devices,
            VulnerabilitiesClient vulnerabilities,
            ActiveDiscoveryClient activeDiscovery,
            TagsClient tags,
            GroupsClient groups,
            CustomNetworkClient networks,
            PresetsClient presets,
            MiscClient misc,
            BaselinesClient baselines,
            SensorExplorerClient sensorExplorer) {

        List<PollingTask<?>> tasks = new ArrayList<>(global(
                sensors, dashboard, devices, vulnerabilities, activeDiscovery,
                tags, groups, networks, presets, misc, baselines, sensorExplorer));

        // 프리셋을 지정하지 않으면 프리셋 단위 폴링은 아예 등록하지 않는다.
        // 빈 맵을 주기적으로 덮어써 봐야 얻는 것이 없다.
        if (!properties.getPresetIds().isEmpty()) {
            tasks.addAll(perPreset(properties, dashboard, devices, vulnerabilities));
        } else {
            log.info("cyber-vision.polling.preset-ids 가 비어 있어 프리셋 단위 폴링을 건너뛴다");
        }
        return List.copyOf(tasks);
    }

    /** 프리셋과 무관하게 전역으로 한 번만 받아오는 항목. */
    private List<PollingTask<?>> global(
            SensorsClient sensors,
            DashboardClient dashboard,
            DevicesClient devices,
            VulnerabilitiesClient vulnerabilities,
            ActiveDiscoveryClient activeDiscovery,
            TagsClient tags,
            GroupsClient groups,
            CustomNetworkClient networks,
            PresetsClient presets,
            MiscClient misc,
            BaselinesClient baselines,
            SensorExplorerClient sensorExplorer) {

        return List.of(
                // --- 1분: 운영 상태 -------------------------------------------------
                new PollingTask<>(StateKeys.SENSORS, FAST,
                        sensors::getSensorList),
                new PollingTask<>(StateKeys.EVENTS_BY_SEVERITY, FAST,
                        () -> dashboard.getEventsCountsBySeverities(null)),
                new PollingTask<>(StateKeys.EVENTS_BY_CATEGORY, FAST,
                        () -> dashboard.getEventsCountsByCategories(null)),

                // --- 5분: 집계 지표 -------------------------------------------------
                new PollingTask<>(StateKeys.DASHBOARD_COUNTS, NORMAL,
                        () -> dashboard.getDashboardCount(null, null)),
                new PollingTask<>(StateKeys.RISK_SCORES, NORMAL,
                        () -> dashboard.getRiskScoreDevicesCount(null, null)),
                new PollingTask<>(StateKeys.VULNERABLE_DEVICES, NORMAL,
                        () -> dashboard.getVulnerableDevicesMetricsAndTop(null, null)),
                new PollingTask<>(StateKeys.PROTOCOL_DISTRIBUTION, NORMAL,
                        () -> dashboard.getProtocolDistributionMetricsAndTop(null, null)),

                // --- 30분: 목록 -----------------------------------------------------
                new PollingTask<>(StateKeys.DEVICES, SLOW,
                        () -> devices.getAll(null, null, null, null, null)),
                new PollingTask<>(StateKeys.VULNERABILITIES, SLOW,
                        () -> vulnerabilities.getVulnerabilityList(null, null, null, null)),
                new PollingTask<>(StateKeys.ACTIVE_DISCOVERY_PROFILES, SLOW,
                        activeDiscovery::listProfile),
                new PollingTask<>(StateKeys.TAGS, SLOW,
                        tags::tagList),

                // --- 6시간: 구성 정보 -----------------------------------------------
                new PollingTask<>(StateKeys.GROUPS, RARE,
                        groups::listGroups),
                new PollingTask<>(StateKeys.CUSTOM_NETWORKS, RARE,
                        networks::getCustomNetworkSettingsList),
                new PollingTask<>(StateKeys.PRESETS, RARE,
                        () -> presets.getPresetList(null)),
                new PollingTask<>(StateKeys.VERSION, RARE,
                        misc::getVersion),
                new PollingTask<>(StateKeys.BASELINES, SLOW,
                        baselines::listBaselines),
                new PollingTask<>(StateKeys.SENSOR_FOLDERS, RARE,
                        sensorExplorer::getLightFolderList));
    }

    /**
     * 프리셋 단위로 받아오는 항목.
     *
     * <p>호출 수는 프리셋 수에 비례하고, 보안 인사이트는 프리셋 × 프로토콜 유형만큼 곱해진다.
     * 그래서 대상 프리셋을 설정으로 좁히도록 했다.
     */
    private List<PollingTask<?>> perPreset(
            PollingProperties properties,
            DashboardClient dashboard,
            DevicesClient devices,
            VulnerabilitiesClient vulnerabilities) {

        List<String> presetIds = properties.getPresetIds();
        List<String> types = properties.getSecurityInsightTypes();
        log.info("프리셋 단위 폴링 - 프리셋 {}개, 보안 인사이트 유형 {}개 (회차당 최대 {}호출)",
                presetIds.size(), types.size(), presetIds.size() * (4 + 2 * types.size()));

        return List.of(
                // --- 5분: 집계 -----------------------------------------------------
                new PollingTask<>(StateKeys.PRESET_DASHBOARD_COUNTS, NORMAL,
                        PresetFanOut.byPreset(presetIds,
                                presetId -> dashboard.getDashboardCountForPreset(presetId, null, null))),
                new PollingTask<>(StateKeys.SECURITY_INSIGHT_COUNTS, NORMAL,
                        PresetFanOut.byPresetAndType(presetIds, types,
                                (presetId, type) -> dashboard.getSecurityDashboardCountForPreset(
                                        presetId, type, null, null))),

                // --- 30분: 목록 -----------------------------------------------------
                new PollingTask<>(StateKeys.SECURITY_INSIGHT_LIST, SLOW,
                        PresetFanOut.byPresetAndType(presetIds, types,
                                (presetId, type) -> dashboard.getSecurityDashboardListForPreset(
                                        presetId, type, null, null, null, null, null, null))),
                new PollingTask<>(StateKeys.PRESET_TAGLESS_FLOWS, SLOW,
                        PresetFanOut.byPreset(presetIds,
                                presetId -> dashboard.getSecurityDashboardTaglessFlowListForPreset(
                                        presetId, null, null, null, null, null, null))),
                new PollingTask<>(StateKeys.PRESET_VULNERABILITIES, SLOW,
                        PresetFanOut.byPreset(presetIds,
                                presetId -> vulnerabilities.getVulnerabilityListForPreset(
                                        presetId, null, null, null, null, null, null, null))),
                new PollingTask<>(StateKeys.PRESET_NETWORK_NODES, SLOW,
                        PresetFanOut.byPreset(presetIds,
                                presetId -> devices.getDeviceList(
                                        presetId, null, null, null, null, null, null, null, null))));
    }
}
