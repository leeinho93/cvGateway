package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Activity;
import com.cvGateway.cvGateway.cybervision.model.ActivityBetweenEndpoints;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Activities}.
 *
 * <p><b>액티비티 (Activities)</b>
 * 두 네트워크 노드 사이에 오간 통신을 하나로 묶은 단위. 액티비티 하나는 여러 플로우를 포함한다.
 *
 * <p>오퍼레이션 6개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionActivities",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ActivitiesClient {

    /**
     * List Activities
     *
     * <p>Activity list.
     *
     * <p>액티비티 목록을 조회한다.
     *
     * <p>{@code GET /activities}
     *
     * <p>{@code 400} - bad request (parameters in wrong format)
     * <p>{@code 401} - incorrect token
     *
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 액티비티 목록
     */
    @GetMapping("/activities")
    List<Activity> getActivityList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Activity Details Between Endpoints
     *
     * <p>Activity details between endpoints.
     *
     * <p>두 엔드포인트 사이의 액티비티 상세를 조회한다.
     *
     * <p>{@code GET /activities/{id}}
     *
     * <p>{@code 400} - bad request (id not in the expected format)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - flows not found
     *
     * @param id pair of endpoints ids (uuid) separated by an underscore, each pair separated by a semicolon. Example: cmpId1_cmpId2;deviceId1_cmpId3;deviceId2_deviceId3 (밑줄로 이은 엔드포인트 ID(UUID) 쌍. 쌍끼리는 세미콜론으로 구분한다. 예: cmpId1_cmpId2;deviceId1_cmpId3)
     * @return 액티비티 상세
     */
    @GetMapping("/activities/{id}")
    List<Activity> getActivity(@PathVariable("id") String id);

    /**
     * List Flows of an Activity
     *
     * <p>액티비티에 속한 플로우 목록을 조회한다.
     *
     * <p>{@code GET /activities/{id}/flows}
     *
     * <p>{@code 400} - bad request (parameters in wrong format)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - activity not found
     *
     * @param id activity uuid or pair of network nodes ids (uuid) separated by an underscore, each pair separated by a semicolon. Example: cmpId1_cmpId2;deviceId1_cmpId3;deviceId2_deviceId3 (액티비티 UUID, 또는 밑줄로 이은 네트워크 노드 ID(UUID) 쌍. 쌍끼리는 세미콜론으로 구분한다)
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @GetMapping("/activities/{id}/flows")
    List<List<Flow>> getActivityFlowList(
            @PathVariable("id") String id,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Flow Tags for an Activity
     *
     * <p>Flow tag list of an activity.
     *
     * <p>액티비티에 속한 플로우들의 태그 목록을 조회한다.
     *
     * <p>{@code GET /activities/{id}/flows/tags}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - activity not found
     *
     * @param id activity uuid or pair of network nodes ids (uuid) separated by an underscore, each pair separated by a semicolon. Example: cmpId1_cmpId2;deviceId1_cmpId3;deviceId2_deviceId3 (액티비티 UUID, 또는 밑줄로 이은 네트워크 노드 ID(UUID) 쌍. 쌍끼리는 세미콜론으로 구분한다)
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 플로우 태그 목록
     */
    @GetMapping("/activities/{id}/flows/tags")
    List<List<DetailedTag>> getActivityFlowTagList(
            @PathVariable("id") String id,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Activities Preview for a Preset
     *
     * <p>Activity list preview for a preset.
     *
     * <p>프리셋 미리보기 기준으로 액티비티 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/activity-list}
     *
     * <p>{@code 400} - bad request (preview or parameters in wrong format)
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 액티비티 목록
     */
    @PostMapping("/presets/preview/visualisations/activity-list")
    List<Activity> getActivityListForPresetPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Activities for a Preset
     *
     * <p>List the activities for a preset. Warning: this endpoint is cached. This means that the returned
     * data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋에 속한 액티비티 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신 데이터가 아닐 수 있다. 실시간 데이터와 함께 쓸 때 주의하고, 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를
     * 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/activity-list}
     *
     * <p>{@code 400} - bad request (parameters in wrong format)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown preset
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 액티비티 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/activity-list")
    List<ActivityBetweenEndpoints> getActivityListForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
