package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.Graph;
import com.cvGateway.cvGateway.cybervision.model.GraphResponse;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Graph}.
 *
 * <p><b>그래프 (Graph)</b>
 * 프리셋에 속한 노드와 간선을 그래프 형태로 반환한다.
 *
 * <p>오퍼레이션 2개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionGraph",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface GraphClient {

    /**
     * Get Graph Map for Preset Preview
     *
     * <p>Return a graph map for a preset preview.
     *
     * <p>프리셋 미리보기 기준으로 그래프 맵을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/graph/{format}}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found
     *
     * @param format graph format (그래프 형식)
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param preview preview payload (미리보기 페이로드)
     * @return 그래프 데이터
     */
    @PostMapping("/presets/preview/visualisations/graph/{format}")
    Graph getComponentGraphForPresetPreview(
            @PathVariable("format") String format,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestBody PresetPayload preview);

    /**
     * Get Preset Graph Map
     *
     * <p>Return a graph map display for a preset.
     *
     * <p>프리셋의 그래프 맵을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/graph/{format}}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found
     *
     * @param presetId preset id (프리셋 ID)
     * @param format graph format (그래프 형식)
     * @param from start of the period of the query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period of the query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 그래프 데이터
     */
    @GetMapping("/presets/{preset_id}/visualisations/graph/{format}")
    GraphResponse getComponentGraphForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("format") String format,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to);
}
