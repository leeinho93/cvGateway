package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.GroupDetail;
import com.cvGateway.cvGateway.cybervision.model.PartialUpdate;
import com.cvGateway.cvGateway.cybervision.model.Preset;
import com.cvGateway.cvGateway.cybervision.model.PresetDataInfo;
import com.cvGateway.cvGateway.cybervision.model.PresetHighlight;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import com.cvGateway.cvGateway.cybervision.model.PresetSettings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Presets}.
 *
 * <p><b>프리셋 (Presets)</b>
 * 조회 범위를 미리 정의해 둔 필터. 다른 태그의 상당수 오퍼레이션이 프리셋 단위로 데이터를 반환한다.
 *
 * <p>오퍼레이션 13개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionPresets",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface PresetsClient {

    /**
     * Get Highlight Information of Favorite Presets
     *
     * <p>Please note that presets are not refreshed by this API.
     *
     * <p>즐겨찾기한 프리셋의 하이라이트 정보를 조회한다.
     *
     * <p>이 오퍼레이션은 프리셋 데이터를 갱신하지 않는다.
     *
     * <p>{@code GET /homepage/presets-highlights}
     *
     * <p>{@code 400} - bad request (for instance wrong format in dates)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - no favorite presets found
     *
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param favoriteType filter which type of favorite presets to retrieve (all, operational or security). (조회할 즐겨찾기 프리셋 종류 (all, operational, security))
     * @return 하이라이트 정보
     */
    @GetMapping("/homepage/presets-highlights")
    PresetHighlight getPresetHighlight(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "favoriteType", required = false) String favoriteType);

    /**
     * List Presets
     *
     * <p>Preset list.
     *
     * <p>프리셋 목록을 조회한다.
     *
     * <p>{@code GET /presets}
     *
     * <p>{@code 401} - incorrect token
     *
     * @param baselines whether to return the baselines (베이스라인을 함께 반환할지 여부)
     * @return 프리셋 목록
     */
    @GetMapping("/presets")
    List<Preset> getPresetList(@RequestParam(name = "baselines", required = false) Boolean baselines);

    /**
     * Create a Preset
     *
     * <p>프리셋을 생성한다.
     *
     * <p>{@code POST /presets}
     *
     * <p>{@code 400} - bad request (payload in unexpected format, for instance)
     * <p>{@code 401} - incorrect token
     * <p>{@code 412} - precondition failed on some field of the body
     *
     * @param preset preset payload (프리셋 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/presets")
    Preset createPreset(@RequestBody PresetPayload preset);

    /**
     * Get Preset Details
     *
     * <p>Preset details.
     *
     * <p>프리셋 상세를 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @return 프리셋 상세
     */
    @GetMapping("/presets/{preset_id}")
    Preset getPreset(@PathVariable("preset_id") String presetId);

    /**
     * Update a Preset
     *
     * <p>프리셋을 수정한다.
     *
     * <p>{@code PUT /presets/{preset_id}}
     *
     * <p>{@code 400} - bad request (payload in unexpected format, or not custom preset, for instance)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset, tag or group not found
     * <p>{@code 409} - There is another preset that already has the label we want to set
     * <p>{@code 412} - precondition failed on some field of the body
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param preset preset payload (프리셋 페이로드)
     * @return 수정 결과
     */
    @PutMapping("/presets/{preset_id}")
    Preset updatePreset(
            @PathVariable("preset_id") String presetId,
            @RequestBody PresetPayload preset);

    /**
     * Update One Preset Property
     *
     * <p>Update one preset property. For the moment, add and remove on components are implemented. For more
     * information, see https://tools.ietf.org/html/rfc6902
     *
     * <p>프리셋의 속성 하나를 부분 수정한다.
     *
     * <p>형식은 RFC 6902(JSON Patch)를 따른다.
     *
     * <p>{@code PATCH /presets/{preset_id}}
     *
     * <p>{@code 400} - bad request. payload in unexpected format, less than 1 or more than 2 patches
     * <p>{@code 401} - incorrect token
     * <p>{@code 403} - forbidden. The "path" is not allowed
     * <p>{@code 404} - No preset found.
     * <p>{@code 412} - precondition failed on some field of the body (too long)
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param preset partial update payload. accepted "op" is "set". accepted path are "/name" (or "/label"), "/description" and "/search" (부분 수정 페이로드. op 는 "set" 만 가능하며 path 는 "/name"(또는 "/label"), "/description", "/search" 를 허용한다)
     * @return 수정 결과
     */
    @PatchMapping("/presets/{preset_id}")
    GroupDetail patchPreset(
            @PathVariable("preset_id") String presetId,
            @RequestBody PartialUpdate preset);

    /**
     * Delete a Preset
     *
     * <p>프리셋을 삭제한다.
     *
     * <p>{@code DELETE /presets/{preset_id}}
     *
     * <p>{@code 400} - bad request (failed to delete non custom preset)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @return 삭제 결과
     */
    @DeleteMapping("/presets/{preset_id}")
    String deletePreset(@PathVariable("preset_id") String presetId);

    /**
     * Get Refresh Metadata About Preset Data
     *
     * <p>This endpoint returns the date when the data was refreshed for the last time for instance.
     *
     * <p>프리셋 데이터의 갱신 메타데이터를 조회한다.
     *
     * <p>데이터가 마지막으로 갱신된 시각 등을 확인할 수 있다.
     *
     * <p>{@code GET /presets/{preset_id}/dataInfo}
     *
     * <p>{@code 400} - bad request (preset id is unexpected format, for instance)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @return 갱신 메타데이터
     */
    @GetMapping("/presets/{preset_id}/dataInfo")
    PresetDataInfo dataInfo(@PathVariable("preset_id") String presetId);

    /**
     * Refresh Preset Data as Soon as Possible
     *
     * <p>Ask to refresh preset data as soon as possible. This data is used as a cache by some endpoints (see
     * the description of such endpoints).
     *
     * <p>프리셋 데이터를 가능한 한 빨리 갱신하도록 요청한다.
     *
     * <p>이 데이터는 여러 조회 오퍼레이션이 캐시로 사용한다.
     *
     * <p>{@code POST /presets/{preset_id}/refreshData}
     *
     * <p>{@code 400} - bad request (preset id is unexpected format, for instance)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @return 요청 결과
     */
    @PostMapping("/presets/{preset_id}/refreshData")
    String refreshData(@PathVariable("preset_id") String presetId);

    /**
     * Get Preset Settings
     *
     * <p>프리셋 설정을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/settings}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id. (프리셋 ID)
     * @return 프리셋 설정
     */
    @GetMapping("/presets/{preset_id}/settings")
    PresetSettings getPresetSettings(@PathVariable("preset_id") String presetId);

    /**
     * Update Preset Settings
     *
     * <p>Update the preset settings.
     *
     * <p>프리셋 설정을 수정한다.
     *
     * <p>{@code PUT /presets/{preset_id}/settings}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id. (프리셋 ID)
     * @param presetSettings preset settings (프리셋 설정)
     * @return 수정 결과
     */
    @PutMapping("/presets/{preset_id}/settings")
    PresetSettings putPresetSettings(
            @PathVariable("preset_id") String presetId,
            @RequestBody PresetSettings presetSettings);

    /**
     * Add Preset Star Status
     *
     * <p>Add a star of the requested type to the preset. For more information, see
     * https://tools.ietf.org/html/rfc6902
     *
     * <p>프리셋에 즐겨찾기를 표시한다.
     *
     * <p>{@code POST /presets/{preset_id}/starring/{type}}
     *
     * <p>{@code 400} - preset_id is not an uuid or type is not valid
     * <p>{@code 401} - incorrect token
     * <p>{@code 403} - forbidden. The "path" is not allowed
     * <p>{@code 404} - No preset found.
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param type type of starring (즐겨찾기 유형)
     * @return 처리 결과
     */
    @PostMapping("/presets/{preset_id}/starring/{type}")
    String postStarringPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("type") String type);

    /**
     * Remove Preset Star Status
     *
     * <p>Remove star of requested type from the preset. For more information, see
     * https://tools.ietf.org/html/rfc6902
     *
     * <p>프리셋의 즐겨찾기 표시를 해제한다.
     *
     * <p>{@code DELETE /presets/{preset_id}/starring/{type}}
     *
     * <p>{@code 400} - preset_id is not an uuid or type is not valid
     * <p>{@code 401} - incorrect token
     * <p>{@code 403} - forbidden. The "path" is not allowed
     * <p>{@code 404} - No preset found.
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param type type of starring (즐겨찾기 유형)
     * @return 처리 결과
     */
    @DeleteMapping("/presets/{preset_id}/starring/{type}")
    String deleteStarringPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("type") String type);
}
