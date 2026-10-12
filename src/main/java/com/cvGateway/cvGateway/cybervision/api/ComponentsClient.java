package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Component;
import com.cvGateway.cvGateway.cybervision.model.ComponentCredential;
import com.cvGateway.cvGateway.cybervision.model.ComponentFields;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.JsonVulnerabilityFullInfos;
import com.cvGateway.cvGateway.cybervision.model.PartialUpdate;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import com.cvGateway.cvGateway.cybervision.model.Variable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Components}.
 *
 * <p><b>컴포넌트 (Components)</b>
 * 네트워크에서 관측된 개별 통신 주체. 하나의 물리 장비(디바이스)가 여러 컴포넌트를 가질 수 있다.
 *
 * <p>오퍼레이션 11개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionComponents",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ComponentsClient {

    /**
     * List Components
     *
     * <p>Component list.
     *
     * <p>컴포넌트 목록을 조회한다.
     *
     * <p>{@code GET /components}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param aggregated whether to aggregate the components when relevant (가능한 경우 컴포넌트를 합쳐서 반환할지 여부)
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param fields the component fields to return in answer (응답에 포함할 컴포넌트 필드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 컴포넌트 목록
     */
    @GetMapping("/components")
    ComponentFields getComponentList(
            @RequestParam(name = "aggregated", required = false) Boolean aggregated,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "fields", required = false) String fields,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Vulnerability Details for a Component
     *
     * <p>Vulnerability detail of a component.
     *
     * <p>컴포넌트의 특정 취약점 상세를 조회한다.
     *
     * <p>{@code GET /components/{component_id}/vulnerabilities/{vulnerability_id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component or vulnerability not found
     *
     * @param componentId component id (uuid) (컴포넌트 ID (UUID))
     * @param vulnerabilityId vulnerability id (uuid) (취약점 ID (UUID))
     * @return 취약점 상세
     */
    @GetMapping("/components/{component_id}/vulnerabilities/{vulnerability_id}")
    JsonVulnerabilityFullInfos getComponentVulnerabilityDetail(
            @PathVariable("component_id") String componentId,
            @PathVariable("vulnerability_id") String vulnerabilityId);

    /**
     * Get Component Details
     *
     * <p>컴포넌트 상세를 조회한다.
     *
     * <p>{@code GET /components/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - at least one component not found
     *
     * @param id an uuid sequence with ';' as separator (none if there is only one id). (세미콜론으로 구분한 UUID 목록. ID 가 하나면 구분자는 생략한다)
     * @param aggregated whether to aggregate the components when relevant (가능한 경우 컴포넌트를 합쳐서 반환할지 여부)
     * @return 컴포넌트 상세
     */
    @GetMapping("/components/{id}")
    List<Component> getComponent(
            @PathVariable("id") String id,
            @RequestParam(name = "aggregated", required = false) Boolean aggregated);

    /**
     * Update Component
     *
     * <p>Partial update of a component.
     *
     * <p>컴포넌트를 부분 수정한다.
     *
     * <p>{@code PATCH /components/{id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 403} - forbidden. The "op" - "Path" association is not allowed.
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @param group partial update payload. "op" can only be "set" on path "/group". Only one group id or zero are allowed in "value" field. Zero group id means "unset". (부분 수정 페이로드. path 가 "/group" 일 때 op 는 "set" 만 가능하며, value 에는 그룹 ID 를 0개 또는 1개만 넣을 수 있다. 0개는 해제를 뜻한다)
     * @return 수정 결과
     */
    @PatchMapping("/components/{id}")
    List<Component> patchComponent(
            @PathVariable("id") String id,
            @RequestBody PartialUpdate group);

    /**
     * List Credentials for a Component
     *
     * <p>Credential list of a component.
     *
     * <p>컴포넌트의 자격증명 목록을 조회한다.
     *
     * <p>{@code GET /components/{id}/credentials}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @return 자격증명 목록
     */
    @GetMapping("/components/{id}/credentials")
    List<ComponentCredential> getComponentCredentialList(@PathVariable("id") String id);

    /**
     * List Flows for a Component
     *
     * <p>Flow list of a component.
     *
     * <p>컴포넌트의 플로우 목록을 조회한다.
     *
     * <p>{@code GET /components/{id}/flows}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 플로우 목록
     */
    @GetMapping("/components/{id}/flows")
    List<Flow> getComponentFlowList(
            @PathVariable("id") String id,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Flow Tags for a Component
     *
     * <p>Flow tag list of a component.
     *
     * <p>컴포넌트의 플로우 태그 목록을 조회한다.
     *
     * <p>{@code GET /components/{id}/flows/tags}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 플로우 태그 목록
     */
    @GetMapping("/components/{id}/flows/tags")
    List<DetailedTag> getComponentFlowTagList(
            @PathVariable("id") String id,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Variables for a Component
     *
     * <p>Variable list of a component.
     *
     * <p>컴포넌트의 변수 목록을 조회한다.
     *
     * <p>{@code GET /components/{id}/variables}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 변수 목록
     */
    @GetMapping("/components/{id}/variables")
    List<Variable> getComponentVariableList(
            @PathVariable("id") String id,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Vulnerabilities for a Component
     *
     * <p>Vulnerability list of a component.
     *
     * <p>컴포넌트의 취약점 목록을 조회한다.
     *
     * <p>{@code GET /components/{id}/vulnerabilities}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - component not found
     *
     * @param id component id (uuid) (컴포넌트 ID (UUID))
     * @return 취약점 목록
     */
    @GetMapping("/components/{id}/vulnerabilities")
    List<JsonVulnerabilityFullInfos> getComponentVulnerabilityList(@PathVariable("id") String id);

    /**
     * List Components Preview for a Preset
     *
     * <p>Component list preview for a preset.
     *
     * <p>프리셋 미리보기 기준으로 컴포넌트 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/component-list}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param aggregated whether to aggregate the components when relevant (가능한 경우 컴포넌트를 합쳐서 반환할지 여부)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 컴포넌트 목록
     */
    @PostMapping("/presets/preview/visualisations/component-list")
    ComponentFields getComponentListForPresetPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "aggregated", required = false) Boolean aggregated,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Components for a Preset
     *
     * <p>Component list for a preset.
     *
     * <p>프리셋에 속한 컴포넌트 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/component-list}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param aggregated whether to aggregate the components when relevant (가능한 경우 컴포넌트를 합쳐서 반환할지 여부)
     * @param format expected format of the returned data. If not specified, json will be used. (반환 데이터 형식. 지정하지 않으면 json)
     * @param fields the component fields to return in answer (응답에 포함할 컴포넌트 필드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 컴포넌트 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/component-list")
    ComponentFields getComponentListForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "aggregated", required = false) Boolean aggregated,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "fields", required = false) String fields,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
