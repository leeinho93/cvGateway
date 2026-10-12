package com.cvGateway.cvGateway.cybervision.web;

import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.apply;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.atLeast;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.contains;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.containsAt;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.countMatching;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.equalTo;
import static com.cvGateway.cvGateway.cybervision.web.CacheQuery.matches;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvGateway.cvGateway.cybervision.cache.CyberVisionStateCache;
import com.cvGateway.cvGateway.cybervision.cache.Freshness;
import com.cvGateway.cvGateway.cybervision.cache.StateSlot;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionNotConfiguredException;
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
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardCounts;
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardElement;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilitiyForList;
import com.cvGateway.cvGateway.cybervision.model.Preset;
import com.cvGateway.cvGateway.cybervision.model.RiskScoreDevicesCountResponse;
import com.cvGateway.cvGateway.cybervision.model.SensorOverview;
import com.cvGateway.cvGateway.cybervision.model.Version;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilityDetail;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventByCategory;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventsBySeverity;
import com.cvGateway.cvGateway.cybervision.model.WidgetProtocolDistributionResponse;
import com.cvGateway.cvGateway.cybervision.model.WidgetVulnerableDevicesResponse;

/**
 * 캐시에 올려 둔 Cyber Vision 현재 상태를 조회하는 API.
 *
 * <p>여기서는 Cyber Vision 을 호출하지 않는다. 폴링 스케줄러가 주기적으로 받아 둔 값을 그대로
 * 돌려주므로 응답이 빠르고, 화면을 보는 사람이 몇 명이든 Center 호출량은 변하지 않는다.
 * 대신 값은 마지막 폴링 시점의 것이며, 응답의 {@code fetchedAt} 과 {@code ageSeconds} 로 확인한다.
 *
 * <p>목록 조회는 공통으로 {@code limit} 과 {@code offset} 을 받고, 항목별로 의미 있는 필터를
 * 추가로 받는다. 필터는 지정하지 않으면 적용되지 않는다. 필터링은 캐시된 목록 위에서 이루어지므로
 * Cyber Vision 쪽에 추가 호출이 발생하지 않는다.
 */
@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
public class CyberVisionCacheController {

    private final CyberVisionStateCache cache;
    private final CyberVisionConnectionStore connectionStore;


    // ------------------------------------------------------------------ 현황

    /**
     * 전체 캐시 항목의 갱신 시각과 실패 여부.
     *
     * <p>어떤 항목이 언제 갱신됐고 무엇이 실패 중인지 한눈에 본다. 폴링이 꺼져 있으면 비어 있다.
     */
    @GetMapping
    public Map<String, Freshness> freshness() {
        return cache.freshness();
    }

    // ------------------------------------------------------------------ 운영 상태

    /**
     * 센서 목록.
     *
     * @param name         이름 부분 일치
     * @param model        모델명 완전 일치
     * @param centerId     소속 Center ID 완전 일치
     * @param serialNumber 시리얼 번호 완전 일치
     */
    @GetMapping("/sensors")
    public CachedResponse<List<SensorOverview>> sensors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String centerId,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.sensors(), limit, offset, sensor ->
                contains(sensor.getName(), name)
                        && matches(sensor.getModel(), model)
                        && matches(sensor.getCenterId(), centerId)
                        && matches(sensor.getSerialNumber(), serialNumber));
    }

    /** 심각도별 최근 이벤트. */
    @GetMapping("/events/severity")
    public CachedResponse<WidgetEventsBySeverity> eventsBySeverity() {
        return single(cache.eventsBySeverity());
    }

    /** 카테고리별 최근 이벤트. */
    @GetMapping("/events/category")
    public CachedResponse<WidgetEventByCategory> eventsByCategory() {
        return single(cache.eventsByCategory());
    }

    // ------------------------------------------------------------------ 집계 지표

    /** 홈 대시보드의 객체 종류별 개수. */
    @GetMapping("/dashboard-counts")
    public CachedResponse<AllCounters> dashboardCounts() {
        return single(cache.dashboardCounts());
    }

    /** 리스크 스코어 구간별 디바이스 수. */
    @GetMapping("/risk-scores")
    public CachedResponse<RiskScoreDevicesCountResponse> riskScores() {
        return single(cache.riskScores());
    }

    /** 취약점 심각도별 디바이스 수. */
    @GetMapping("/vulnerable-devices")
    public CachedResponse<WidgetVulnerableDevicesResponse> vulnerableDevices() {
        return single(cache.vulnerableDevices());
    }

    /** 프로토콜별 액티비티 분포. */
    @GetMapping("/protocol-distribution")
    public CachedResponse<WidgetProtocolDistributionResponse> protocolDistribution() {
        return single(cache.protocolDistribution());
    }

    // ------------------------------------------------------------------ 목록

    /**
     * 자산(디바이스) 인벤토리.
     *
     * <p>필터가 없는 이유는 스펙 때문이다. {@code GET /devices} 의 응답 스키마가 목록이 아니라
     * {@code Device} 단건으로 정의돼 있어 캐시도 단건을 담는다. 실제 Center 가 배열을 돌려준다면
     * 스펙 오류이므로, 장비 연동 시 확인한 뒤 목록으로 바꾸고 필터를 붙여야 한다.
     */
    @GetMapping("/devices")
    public CachedResponse<Device> devices() {
        return single(cache.devices());
    }

    /**
     * 탐지된 취약점 목록.
     *
     * @param cve      CVE 식별자 부분 일치
     * @param title    제목 부분 일치
     * @param vendorId 벤더 ID 완전 일치
     * @param minCvss  CVSS 점수 하한 (이상)
     */
    @GetMapping("/vulnerabilities")
    public CachedResponse<List<VulnerabilityDetail>> vulnerabilities(
            @RequestParam(required = false) String cve,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String vendorId,
            @RequestParam(required = false) Double minCvss,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.vulnerabilities(), limit, offset, vulnerability ->
                contains(vulnerability.getCve(), cve)
                        && contains(vulnerability.getTitle(), title)
                        && matches(vulnerability.getVendorId(), vendorId)
                        && atLeast(vulnerability.getCVSS(), minCvss));
    }

    /**
     * 능동 탐색 프로필.
     *
     * @param name             프로필 이름 부분 일치
     * @param schedulingStatus 예약 상태 완전 일치 (예: {@code SCHEDULED}, {@code NOT_SCHEDULED})
     * @param policyId         적용된 정책 ID 완전 일치
     */
    @GetMapping("/active-discovery-profiles")
    public CachedResponse<List<ActiveDiscoveryProfile>> activeDiscoveryProfiles(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String schedulingStatus,
            @RequestParam(required = false) String policyId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.activeDiscoveryProfiles(), limit, offset, profile ->
                contains(profile.getName(), name)
                        && matches(profile.getSchedulingStatus(), schedulingStatus)
                        && matches(profile.getPolicyId(), policyId));
    }

    /**
     * 태그 목록.
     *
     * @param label     라벨 부분 일치
     * @param type      태그 유형 완전 일치
     * @param important 중요 태그 여부
     */
    @GetMapping("/tags")
    public CachedResponse<List<DetailedTag>> tags(
            @RequestParam(required = false) String label,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean important,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.tags(), limit, offset, tag ->
                contains(tag.getLabel(), label)
                        && matches(tag.getType(), type)
                        && equalTo(tag.getImportant(), important));
    }

    // ------------------------------------------------------------------ 구성 정보

    /**
     * 그룹(존) 목록.
     *
     * @param label          라벨 부분 일치
     * @param criticalness   중요도 완전 일치
     * @param minCriticalness 중요도 하한 (이상)
     * @param parentId       상위 그룹 ID 완전 일치. 최상위만 보려면 값을 비워 두는 대신
     *                       {@code rootOnly} 를 쓴다
     * @param rootOnly       {@code true} 면 상위 그룹이 없는 항목만
     */
    @GetMapping("/groups")
    public CachedResponse<List<Group>> groups(
            @RequestParam(required = false) String label,
            // Group.criticalness 는 Long 이다. Integer 로 받으면 equals 비교가 항상 거짓이 된다.
            @RequestParam(required = false) Long criticalness,
            @RequestParam(required = false) Long minCriticalness,
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Boolean rootOnly,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.groups(), limit, offset, group ->
                contains(group.getLabel(), label)
                        && equalTo(group.getCriticalness(), criticalness)
                        && atLeast(group.getCriticalness(), minCriticalness)
                        && matches(group.getParentId(), parentId)
                        && (!Boolean.TRUE.equals(rootOnly) || group.getParentId() == null));
    }

    /**
     * 사용자 정의 네트워크.
     *
     * <p>스펙이 이 응답의 스키마를 정의하지 않아 키-값 맵으로 담긴다. 필터도 맵의 문자열 값에
     * 대한 부분 일치로 동작한다.
     *
     * @param name    {@code name} 필드 부분 일치
     * @param ipRange {@code ipRange} 필드 부분 일치
     */
    @GetMapping("/custom-networks")
    public CachedResponse<List<Map<String, Object>>> customNetworks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String ipRange,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.customNetworks(), limit, offset, network ->
                containsAt(network, "name", name)
                        && containsAt(network, "ipRange", ipRange));
    }

    /**
     * 프리셋 목록.
     *
     * @param label   라벨 부분 일치
     * @param custom  사용자가 만든 프리셋만 볼지 여부
     * @param starred {@code true} 면 운영·보안 즐겨찾기 중 하나라도 표시된 것만
     */
    @GetMapping("/presets")
    public CachedResponse<List<Preset>> presets(
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Boolean custom,
            @RequestParam(required = false) Boolean starred,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.presets(), limit, offset, preset ->
                contains(preset.getLabel(), label)
                        && equalTo(preset.getCustom(), custom)
                        && (!Boolean.TRUE.equals(starred)
                                || Boolean.TRUE.equals(preset.getOperationalStarred())
                                || Boolean.TRUE.equals(preset.getSecurityStarred())));
    }

    /** Center 버전. */
    @GetMapping("/version")
    public CachedResponse<Version> version() {
        return single(cache.version());
    }

    /**
     * 베이스라인 목록.
     *
     * @param label  라벨 부분 일치
     * @param id     ID 완전 일치
     */
    @GetMapping("/baselines")
    public CachedResponse<List<Baseline>> baselines(
            @RequestParam(required = false) String label,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return list(cache.baselines(), limit, offset, baseline ->
                contains(baseline.getLabel(), label)
                        && matches(baseline.getId(), id));
    }

    /** 센서 폴더 트리. */
    @GetMapping("/sensor-folders")
    public CachedResponse<LightFolder> sensorFolders() {
        return single(cache.sensorFolders());
    }

    // ------------------------------------------------------------------ 프리셋 단위

    /*
     * 아래 항목은 cyber-vision.polling.preset-ids 에 지정한 프리셋만 담고 있다.
     * presetId 를 주면 해당 프리셋만, 주지 않으면 담긴 전부를 돌려준다.
     * 어떤 프리셋이 잡혀 있는지는 GET /api/cache/presets 로 확인한다.
     */

    /**
     * 프리셋별 대시보드 집계.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     */
    @GetMapping("/preset/dashboard-counts")
    public CachedResponse<Map<String, AllCountResult>> presetDashboardCounts(
            @RequestParam(required = false) String presetId) {
        return byPreset(cache.presetDashboardCounts(), presetId);
    }

    /**
     * 프리셋별 취약점 목록.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     */
    @GetMapping("/preset/vulnerabilities")
    public CachedResponse<Map<String, List<VulnerabilitiyForList>>> presetVulnerabilities(
            @RequestParam(required = false) String presetId) {
        return byPreset(cache.presetVulnerabilities(), presetId);
    }

    /**
     * 프리셋별 네트워크 노드(디바이스·컴포넌트) 목록.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     */
    @GetMapping("/preset/network-nodes")
    public CachedResponse<Map<String, NetworkNodes>> presetNetworkNodes(
            @RequestParam(required = false) String presetId) {
        return byPreset(cache.presetNetworkNodes(), presetId);
    }

    /**
     * 프리셋별 태그 없는(미분류) 플로우.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     */
    @GetMapping("/preset/tagless-flows")
    public CachedResponse<Map<String, Flow>> presetTaglessFlows(
            @RequestParam(required = false) String presetId) {
        return byPreset(cache.presetTaglessFlows(), presetId);
    }

    /**
     * 프리셋·프로토콜별 보안 인사이트 집계.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     * @param type     특정 프로토콜 유형만 보려면 지정 ({@code dns}, {@code http}, {@code smb})
     */
    @GetMapping("/preset/security-insights/counts")
    public CachedResponse<Map<String, Map<String, SecurityInsightDashboardCounts>>> securityInsightCounts(
            @RequestParam(required = false) String presetId,
            @RequestParam(required = false) String type) {
        return byPresetAndType(cache.securityInsightCounts(), presetId, type);
    }

    /**
     * 프리셋·프로토콜별 보안 인사이트 목록.
     *
     * @param presetId 특정 프리셋만 보려면 지정
     * @param type     특정 프로토콜 유형만 보려면 지정 ({@code dns}, {@code http}, {@code smb})
     */
    @GetMapping("/preset/security-insights/list")
    public CachedResponse<Map<String, Map<String, SecurityInsightDashboardElement>>> securityInsightList(
            @RequestParam(required = false) String presetId,
            @RequestParam(required = false) String type) {
        return byPresetAndType(cache.securityInsightList(), presetId, type);
    }

    // ------------------------------------------------------------------ 공통 처리

    /**
     * 접속 정보가 주입돼 있는지 확인한다.
     *
     * <p>없으면 캐시도 채워질 수 없고 앞으로도 채워지지 않는다. 빈 응답을 돌려주면
     * "데이터가 없다"로 오해하기 쉬우므로, 무엇을 해야 하는지 알려 주는 오류로 끊는다.
     *
     * @throws CyberVisionNotConfiguredException 접속 정보가 주입되지 않은 경우
     */
    private void requireConnection() {
        if (!connectionStore.isConfigured()) {
            throw new CyberVisionNotConfiguredException();
        }
    }

    /** 단일 값 응답을 만든다. */
    private <T> CachedResponse<T> single(StateSlot<T> slot) {
        requireConnection();
        return CachedResponse.of(slot.get().orElse(null), slot.freshness());
    }

    /**
     * 프리셋 ID 를 키로 하는 맵에서 특정 프리셋만 골라 응답한다.
     *
     * <p>{@code presetId} 를 주지 않으면 담긴 전부를 돌려준다. 지정한 프리셋이 캐시에 없으면
     * 빈 맵이 나오는데, 이는 {@code preset-ids} 설정에 포함되지 않았다는 뜻이다.
     */
    private <V> CachedResponse<Map<String, V>> byPreset(
            StateSlot<Map<String, V>> slot, String presetId) {

        requireConnection();
        Map<String, V> all = slot.get().orElse(null);
        if (all == null) {
            return CachedResponse.of(null, slot.freshness());
        }
        return CachedResponse.of(pick(all, presetId), slot.freshness());
    }

    /** 프리셋·유형 2단 맵에서 원하는 조합만 골라 응답한다. */
    private <V> CachedResponse<Map<String, Map<String, V>>> byPresetAndType(
            StateSlot<Map<String, Map<String, V>>> slot, String presetId, String type) {

        requireConnection();
        Map<String, Map<String, V>> all = slot.get().orElse(null);
        if (all == null) {
            return CachedResponse.of(null, slot.freshness());
        }
        Map<String, Map<String, V>> selected = new LinkedHashMap<>();
        pick(all, presetId).forEach((id, perType) -> selected.put(id, pick(perType, type)));
        return CachedResponse.of(selected, slot.freshness());
    }

    /** 키를 지정하면 그 항목만, 지정하지 않으면 전체를 돌려준다. */
    private static <V> Map<String, V> pick(Map<String, V> source, String key) {
        if (key == null || key.isBlank()) {
            return source;
        }
        V value = source.get(key);
        return value == null ? Map.of() : Map.of(key, value);
    }

    /**
     * 목록 응답을 만든다. 필터를 적용해 전체 개수를 센 뒤 페이징한 결과를 담는다.
     *
     * <p>아직 한 번도 받아오지 못했으면 빈 목록이 아니라 "값 없음"으로 응답한다. 폴링이 안 되고
     * 있는 상황과 조건에 맞는 항목이 없는 상황은 화면에서 다르게 다뤄야 하기 때문이다.
     */
    private <E> CachedResponse<List<E>> list(
            StateSlot<List<E>> slot, Integer limit, Integer offset, Predicate<E> filter) {

        requireConnection();
        List<E> items = slot.get().orElse(null);
        if (items == null) {
            return CachedResponse.emptyList(slot.freshness());
        }
        return CachedResponse.ofList(
                apply(items, filter, limit, offset),
                countMatching(items, filter),
                slot.freshness());
    }
}
