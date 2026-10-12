package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.FlowContent;
import com.cvGateway.cvGateway.cybervision.model.FlowDetail;
import com.cvGateway.cvGateway.cybervision.model.FlowProperty;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Flows}.
 *
 * <p><b>플로우 (Flows)</b>
 * 출발지·목적지·포트·프로토콜로 식별되는 개별 통신 흐름. 액티비티를 구성하는 최소 단위다.
 *
 * <p>오퍼레이션 6개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionFlows",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface FlowsClient {

    /**
     * List Flows
     *
     * <p>Flow list.
     *
     * <p>플로우 목록을 조회한다.
     *
     * <p>{@code GET /flows}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @GetMapping("/flows")
    List<Flow> getFlowList(
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Flow Details
     *
     * <p>Flow detail.
     *
     * <p>플로우 상세를 조회한다.
     *
     * <p>{@code GET /flows/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - flow not found
     *
     * @param id activity id (uuid) (액티비티 ID (UUID))
     * @return 플로우 상세
     */
    @GetMapping("/flows/{id}")
    FlowDetail getFlow(@PathVariable("id") String id);

    /**
     * List Flow Contents
     *
     * <p>Flow content list.
     *
     * <p>플로우의 내용 목록을 조회한다.
     *
     * <p>{@code GET /flows/{id}/flowContents}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - flow not found
     *
     * @param id flow id (uuid) (플로우 ID (UUID))
     * @return 플로우 내용 목록
     */
    @GetMapping("/flows/{id}/flowContents")
    List<FlowContent> getFlowContentList(@PathVariable("id") String id);

    /**
     * List Flow Properties
     *
     * <p>플로우의 속성 목록을 조회한다.
     *
     * <p>{@code GET /flows/{id}/properties}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - flow not found
     *
     * @param id flow id (uuid) (플로우 ID (UUID))
     * @return 속성 목록
     */
    @GetMapping("/flows/{id}/properties")
    List<FlowProperty> getFlowProperties(@PathVariable("id") String id);

    /**
     * List Flows Preview for a Preset
     *
     * <p>Flow list preview for a preset.
     *
     * <p>프리셋 미리보기 기준으로 플로우 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/flow-list}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @PostMapping("/presets/preview/visualisations/flow-list")
    List<Flow> getFlowListForPresetPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Flows for a Preset
     *
     * <p>Flow list for a preset.
     *
     * <p>프리셋에 속한 플로우 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/flow-list}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/flow-list")
    List<Flow> getFlowListForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
