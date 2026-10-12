package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.AllCountResult;
import com.cvGateway.cvGateway.cybervision.model.AllCounters;
import com.cvGateway.cvGateway.cybervision.model.ComponentFields;
import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import com.cvGateway.cvGateway.cybervision.model.RiskScoreDevicesCountResponse;
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardCounts;
import com.cvGateway.cvGateway.cybervision.model.SecurityInsightDashboardElement;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventByCategory;
import com.cvGateway.cvGateway.cybervision.model.WidgetEventsBySeverity;
import com.cvGateway.cvGateway.cybervision.model.WidgetProtocolDistributionResponse;
import com.cvGateway.cvGateway.cybervision.model.WidgetVulnerableDevicesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Dashboard}.
 *
 * <p><b>대시보드 (Dashboard)</b>
 * 홈 화면 위젯용 집계 데이터. 대부분 캐시된 값을 돌려주므로 최신 데이터가 필요하면 refresh 계열 오퍼레이션을 먼저 호출해야 한다.
 *
 * <p>오퍼레이션 18개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionDashboard",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface DashboardClient {

    /**
     * Get Recent Events by Category
     *
     * <p>Get the list of the most recent events by category.
     *
     * <p>카테고리별 최근 이벤트 목록을 조회한다.
     *
     * <p>{@code GET /dashboard/events/categories}
     *
     * <p>{@code 400} - bad request (for instance not uuid in centerId param)
     * <p>{@code 401} - incorrect token
     *
     * @param centerId enrolled local center ids for which we want the metrics (지표를 조회할 등록된 로컬 Center ID 목록)
     * @return 카테고리별 이벤트
     */
    @GetMapping("/dashboard/events/categories")
    WidgetEventByCategory getEventsCountsByCategories(@RequestParam(name = "centerId", required = false) String centerId);

    /**
     * Get Severe, Recent Events
     *
     * <p>Get the list of the most severe and recent events.
     *
     * <p>심각도가 높은 최근 이벤트 목록을 조회한다.
     *
     * <p>{@code GET /dashboard/events/severities}
     *
     * <p>{@code 400} - bad request (for instance not uuid in centerId param)
     * <p>{@code 401} - incorrect token
     *
     * @param centerId enrolled local center ids for which we want the metrics (지표를 조회할 등록된 로컬 Center ID 목록)
     * @return 심각도별 이벤트
     */
    @GetMapping("/dashboard/events/severities")
    WidgetEventsBySeverity getEventsCountsBySeverities(@RequestParam(name = "centerId", required = false) String centerId);

    /**
     * Ping System for Dashboard Widgets
     *
     * <p>Ping system for dashboard widgets. This is useful to increase the refresh priority of the cached
     * data used by all widgets.
     *
     * <p>대시보드 위젯이 사용 중임을 알려 캐시 갱신 우선순위를 높인다.
     *
     * <p>{@code POST /dashboard/ping}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 처리 결과
     */
    @PostMapping("/dashboard/ping")
    String ping();

    /**
     * Get the Activity Count Per Protocol
     *
     * <p>Get the activity count per protocol. Metrics only if we are connected on local center or standalone.
     * Metrics and top centers if we are connected on global center filter by enrolled local center in
     * params Warning: this endpoint is cached. This means that the returned data is not necessarily the
     * latest. The cache is automatically updated, but can also be updated at will thanks to POST
     * /dashboard/{widget_id}/refresh. More over, we can notify we are using this data with POST
     * /dashboard/ping, and then increase its refresh priority.
     *
     * <p>프로토콜별 액티비티 수를 조회한다.
     *
     * <p>로컬 Center 또는 독립 구성에서는 지표만 반환하고, 글로벌 Center 에 접속한 경우 등록된 로컬 Center 기준의 상위 목록까지 반환한다. 캐시된 응답이므로 최신이 아닐
     * 수 있다.
     *
     * <p>{@code GET /dashboard/protocols/counts}
     *
     * <p>{@code 400} - bad request (for instance not uuid in centerId param)
     * <p>{@code 401} - incorrect token
     *
     * @param centerId enrolled local center id for which we want the metrics (지표를 조회할 등록된 로컬 Center ID)
     * @param limit number of centers sent in payload (응답에 포함할 Center 수)
     * @return 프로토콜별 분포
     */
    @GetMapping("/dashboard/protocols/counts")
    WidgetProtocolDistributionResponse getProtocolDistributionMetricsAndTop(
            @RequestParam(name = "centerId", required = false) String centerId,
            @RequestParam(name = "limit", required = false) String limit);

    /**
     * Get Risk Score Devices Counts Per Center
     *
     * <p>Warning: this endpoint is cached. This means that the returned data is not necessarily the latest.
     * The cache is automatically updated, but can also be updated at will thanks to POST
     * /dashboard/{widget_id}/refresh. More over, we can notify we are using this data with POST
     * /dashboard/ping, and then increase its refresh priority.
     *
     * <p>Center 별 리스크 스코어 구간별 디바이스 수를 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. POST /dashboard/{widget_id}/refresh 로 즉시 갱신하거나, POST /dashboard/ping 으로 사용 중임을
     * 알려 갱신 우선순위를 올릴 수 있다.
     *
     * <p>{@code GET /dashboard/risk-score/devices/counts}
     *
     * <p>{@code 400} - bad request (for instance not uuid in centerId param)
     * <p>{@code 401} - incorrect token
     *
     * @param centerId enrolled local center ids for which we want the metrics (지표를 조회할 등록된 로컬 Center ID 목록)
     * @param limit number of centers sent in payload (default 5) (응답에 포함할 Center 수 (기본값 5))
     * @return 리스크 스코어별 디바이스 수
     */
    @GetMapping("/dashboard/risk-score/devices/counts")
    RiskScoreDevicesCountResponse getRiskScoreDevicesCount(
            @RequestParam(name = "centerId", required = false) String centerId,
            @RequestParam(name = "limit", required = false) String limit);

    /**
     * Get Number of Devices Per Vulnerability Severity
     *
     * <p>Get number of devices per vulnerability severity. Metrics only if we are connected on local center
     * or standalone. Metrics and top centers if we are connected on global center filter by enrolled local
     * center in params Warning: this endpoint is cached. This means that the returned data is not
     * necessarily the latest. The cache is automatically updated, but can also be updated at will thanks
     * to POST /dashboard/{widget_id}/refresh. More over, we can notify we are using this data with POST
     * /dashboard/ping, and then increase its refresh priority.
     *
     * <p>취약점 심각도별 디바이스 수를 조회한다.
     *
     * <p>로컬 Center 또는 독립 구성에서는 지표만 반환하고, 글로벌 Center 에 접속한 경우 등록된 로컬 Center 기준의 상위 목록까지 반환한다. 캐시된 응답이므로 최신이 아닐
     * 수 있다.
     *
     * <p>{@code GET /dashboard/vulnerabilities/counts}
     *
     * <p>{@code 400} - bad request (for instance not uuid in centerId param)
     * <p>{@code 401} - incorrect token
     *
     * @param centerId local center id we want metrics for (지표를 조회할 로컬 Center ID)
     * @param limit number of centers sent in payload (응답에 포함할 Center 수)
     * @return 심각도별 디바이스 수
     */
    @GetMapping("/dashboard/vulnerabilities/counts")
    WidgetVulnerableDevicesResponse getVulnerableDevicesMetricsAndTop(
            @RequestParam(name = "centerId", required = false) String centerId,
            @RequestParam(name = "limit", required = false) String limit);

    /**
     * Refresh Widget Data
     *
     * <p>Refresh widget data. This data is used as a cache by all dashboard widgets.
     *
     * <p>위젯 데이터를 갱신한다.
     *
     * <p>이 데이터는 모든 대시보드 위젯이 캐시로 공유한다.
     *
     * <p>{@code POST /dashboard/{widget_id}/refresh}
     *
     * <p>{@code 400} - bad request (for instance no widget_id provided)
     * <p>{@code 401} - incorrect token
     *
     * @param widgetId id of the widget to refresh (authorized values are latestEventsBySeverities, latestEventByCategory, vulnerableDevices, topTalkers, protocolDistribution and riskScore) (갱신할 위젯 ID. 허용값: latestEventsBySeverities, latestEventByCategory, vulnerableDevices, topTalkers, protocolDistribution, riskScore)
     * @return 처리 결과
     */
    @PostMapping("/dashboard/{widget_id}/refresh")
    String refreshWidget(@PathVariable("widget_id") String widgetId);

    /**
     * Count Object Types for Dashboard
     *
     * <p>대시보드에 표시할 객체 종류별 개수를 집계한다.
     *
     * <p>{@code GET /homepage/dashboard}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 객체 종류별 개수
     */
    @GetMapping("/homepage/dashboard")
    AllCounters getDashboardCount(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);

    /**
     * Count Object Types for Dashboard Preview
     *
     * <p>Count every kind of object to display in the dashboard for a preview.
     *
     * <p>프리셋 미리보기 기준으로 대시보드 객체 종류별 개수를 집계한다.
     *
     * <p>{@code POST /presets/preview/visualisations/dashboard}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 객체 종류별 개수
     */
    @PostMapping("/presets/preview/visualisations/dashboard")
    AllCountResult getDashboardCountForPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);

    /**
     * List Security Insights Tagless Flows for Presets
     *
     * <p>List security insights tagless flows for the dashboard for a preview.
     *
     * <p>프리셋 미리보기 기준으로 태그 없는 플로우 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/dashboard-security/tagless-flows}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @PostMapping("/presets/preview/visualisations/dashboard-security/tagless-flows")
    Flow getSecurityDashboardTaglessFlowListForPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Security Insights Components for Presets
     *
     * <p>List security insights components for the dashboard for a preview.
     *
     * <p>프리셋 미리보기 기준으로 보안 인사이트 컴포넌트 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/dashboard-security/{type}/components}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param type protocol type (프로토콜 유형)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @param propertyValue the value of the property corresponding to the protocol type for filtering (필터링에 사용할, 프로토콜 유형에 대응하는 속성 값)
     * @return 컴포넌트 목록
     */
    @PostMapping("/presets/preview/visualisations/dashboard-security/{type}/components")
    ComponentFields getSecurityDashboardComponentListForPreview(
            @RequestBody PresetPayload preview,
            @PathVariable("type") String type,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "property-value", required = true) String propertyValue);

    /**
     * Count Security Insights for Dashboard Preview
     *
     * <p>Count security insights to display in the dashboard for a preview.
     *
     * <p>프리셋 미리보기 기준으로 보안 인사이트 개수를 집계한다.
     *
     * <p>{@code POST /presets/preview/visualisations/dashboard-security/{type}/counts}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param type protocol type (프로토콜 유형)
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 보안 인사이트 집계
     */
    @PostMapping("/presets/preview/visualisations/dashboard-security/{type}/counts")
    SecurityInsightDashboardCounts getSecurityDashboardCountForPreview(
            @RequestBody PresetPayload preview,
            @PathVariable("type") String type,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);

    /**
     * List Security Insights for Dashboard Preview
     *
     * <p>List security insights for the dashboard for a preview.
     *
     * <p>프리셋 미리보기 기준으로 보안 인사이트 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/dashboard-security/{type}/list}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param type protocol type (프로토콜 유형)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 보안 인사이트 목록
     */
    @PostMapping("/presets/preview/visualisations/dashboard-security/{type}/list")
    SecurityInsightDashboardElement getSecurityDashboardListForPreview(
            @RequestBody PresetPayload preview,
            @PathVariable("type") String type,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Count Preset Object Types for Dashboard
     *
     * <p>Count preset object types for dashboard. Warning: this endpoint is cached. This means that the
     * returned data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋 기준으로 대시보드 객체 종류별 개수를 집계한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/dashboard}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 객체 종류별 개수
     */
    @GetMapping("/presets/{preset_id}/visualisations/dashboard")
    AllCountResult getDashboardCountForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);

    /**
     * List Tagless Flows for Preset Dashboard
     *
     * <p>List tagless flows for preset dashboard. Warning: this endpoint is cached. This means that the
     * returned data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋 대시보드의 태그 없는 플로우 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/dashboard-security/tagless-flows}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/dashboard-security/tagless-flows")
    Flow getSecurityDashboardTaglessFlowListForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Security Insights Components for Preset Dashboard
     *
     * <p>프리셋 대시보드의 보안 인사이트 컴포넌트 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/dashboard-security/{type}/components}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param type protocol type (프로토콜 유형)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @param propertyValue the value of the property corresponding to the protocol type for filtering (필터링에 사용할, 프로토콜 유형에 대응하는 속성 값)
     * @return 컴포넌트 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/dashboard-security/{type}/components")
    ComponentFields getSecurityDashboardComponentListForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("type") String type,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "property-value", required = true) String propertyValue);

    /**
     * Count Security Insights for Preset Dashboard
     *
     * <p>Count security insights for preset dashboard. Warning: this endpoint is cached. This means that the
     * returned data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋 대시보드의 보안 인사이트 개수를 집계한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/dashboard-security/{type}/counts}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param type protocol type (프로토콜 유형)
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 보안 인사이트 집계
     */
    @GetMapping("/presets/{preset_id}/visualisations/dashboard-security/{type}/counts")
    SecurityInsightDashboardCounts getSecurityDashboardCountForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("type") String type,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);

    /**
     * List Security Insights for Preset Dashboard
     *
     * <p>List security insights for preset dashboard. Warning: this endpoint is cached. This means that the
     * returned data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋 대시보드의 보안 인사이트 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/dashboard-security/{type}/list}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param type protocol type (프로토콜 유형)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 보안 인사이트 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/dashboard-security/{type}/list")
    SecurityInsightDashboardElement getSecurityDashboardListForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("type") String type,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
