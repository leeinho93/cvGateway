package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Tags}.
 *
 * <p><b>태그 (Tags)</b>
 * 컴포넌트와 플로우에 자동 부여되는 분류 라벨.
 *
 * <p>오퍼레이션 3개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionTags",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface TagsClient {

    /**
     * List Tags from Preview Preset
     *
     * <p>Get tag list from preview preset.
     *
     * <p>프리셋 미리보기 기준으로 태그 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/tag-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     *
     * @param preset preset payload (프리셋 페이로드)
     * @param from start time of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end time of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param type the tag type (태그 유형)
     * @return 태그 목록
     */
    @PostMapping("/presets/preview/visualisations/tag-list")
    List<DetailedTag> tagListFromPreview(
            @RequestBody PresetPayload preset,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "type", required = false) String type);

    /**
     * Get Tags List from a Preset
     *
     * <p>List the tags for a preset. Warning: this endpoint is cached. This means that the returned data is
     * not necessarily the latest. So, be careful when using this endpoint with live data endpoints. The
     * cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋의 태그 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/tag-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId the preset id to get tags list. (태그 목록을 조회할 프리셋 ID)
     * @param from start time of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end time of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param type the tag type (태그 유형)
     * @return 태그 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/tag-list")
    List<DetailedTag> tagListFromPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "type", required = false) String type);

    /**
     * Get Tags List
     *
     * <p>태그 목록을 조회한다.
     *
     * <p>{@code GET /tags}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 태그 목록
     */
    @GetMapping("/tags")
    List<DetailedTag> tagList();
}
