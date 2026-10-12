package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Activity;
import com.cvGateway.cvGateway.cybervision.model.Baseline;
import com.cvGateway.cvGateway.cybervision.model.BaselineComponentFields;
import com.cvGateway.cvGateway.cybervision.model.BaselineCreate;
import com.cvGateway.cvGateway.cybervision.model.BaselinePatch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Baselines}.
 *
 * <p><b>베이스라인 (Baselines)</b>
 * 정상으로 간주하는 네트워크 상태의 스냅샷. 이후 변화를 이 기준과 비교해 이상을 탐지한다.
 *
 * <p>오퍼레이션 8개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionBaselines",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface BaselinesClient {

    /**
     * List Baselines
     *
     * <p>베이스라인 목록을 조회한다.
     *
     * <p>{@code GET /baselines}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     *
     * @return 베이스라인 목록
     */
    @GetMapping("/baselines")
    List<Baseline> listBaselines();

    /**
     * Create a Baseline
     *
     * <p>베이스라인을 생성한다.
     *
     * <p>{@code POST /baselines}
     *
     * <p>{@code 400} - bad request (fields in payload in unexpected format, for instance)
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 409} - There is another baseline that already has the same label
     * <p>{@code 412} - precondition failed on some field of the body
     *
     * @param baseline create baseline payload (베이스라인 생성 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/baselines")
    Baseline createBaseline(@RequestBody BaselineCreate baseline);

    /**
     * Get Details of a Baseline
     *
     * <p>베이스라인 상세를 조회한다.
     *
     * <p>{@code GET /baselines/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline not found
     *
     * @param id baseline id (uuid). Example: cbcbed07-1ed4-4fa6-817c-f3c1702fd187 (베이스라인 ID (UUID))
     * @return 베이스라인 상세
     */
    @GetMapping("/baselines/{id}")
    Baseline getBaseline(@PathVariable("id") String id);

    /**
     * Update Label And/or Description of Given Baseline
     *
     * <p>베이스라인의 라벨 또는 설명을 수정한다.
     *
     * <p>{@code PATCH /baselines/{id}}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline not found
     * <p>{@code 409} - conflict, another baseline already exists with the same label
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @param baseline baseline patch payload (베이스라인 부분 수정 페이로드)
     * @return 수정 결과
     */
    @PatchMapping("/baselines/{id}")
    Baseline patchBaseline(
            @PathVariable("id") String id,
            @RequestBody BaselinePatch baseline);

    /**
     * Delete a Baseline
     *
     * <p>베이스라인을 삭제한다.
     *
     * <p>{@code DELETE /baselines/{id}}
     *
     * <p>{@code 204} - deletion successful or nothing to delete
     * <p>{@code 400} - bad request (failed to delete non custom preset)
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     */
    @DeleteMapping("/baselines/{id}")
    void deleteBaseline(@PathVariable("id") String id);

    /**
     * List Activities for a Baseline
     *
     * <p>Activity list for a baseline.
     *
     * <p>베이스라인에 속한 액티비티 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{id}/visualisations/activity-list}
     *
     * <p>{@code 400} - bad request (parameters in wrong format)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown baseline or null id
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 액티비티 목록
     */
    @GetMapping("/baselines/{id}/visualisations/activity-list")
    List<Activity> getActivityListForBaseline(
            @PathVariable("id") String id,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Components for a Baseline
     *
     * <p>Component list for a baseline.
     *
     * <p>베이스라인에 속한 컴포넌트 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{id}/visualisations/component-list}
     *
     * <p>{@code 400} - bad request (parameters in wrong format)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown baseline or null id
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 컴포넌트 목록
     */
    @GetMapping("/baselines/{id}/visualisations/component-list")
    List<BaselineComponentFields> getComponentListForBaseline(
            @PathVariable("id") String id,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Baselines for a Preset
     *
     * <p>Baseline list for a given preset.
     *
     * <p>프리셋에 속한 베이스라인 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/baselines}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @return 베이스라인 목록
     */
    @GetMapping("/presets/{preset_id}/baselines")
    List<Baseline> getBaselineListForPreset(@PathVariable("preset_id") String presetId);
}
