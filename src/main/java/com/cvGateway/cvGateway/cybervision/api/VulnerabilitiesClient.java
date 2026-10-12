package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.AckVulnerabilityForDevices;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import com.cvGateway.cvGateway.cybervision.model.SimpleResponse;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilitiyForList;
import com.cvGateway.cvGateway.cybervision.model.VulnerabilityDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Vulnerabilities}.
 *
 * <p><b>취약점 (Vulnerabilities)</b>
 * 탐지된 취약점 조회와 승인(acknowledge) 처리.
 *
 * <p>오퍼레이션 9개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionVulnerabilities",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface VulnerabilitiesClient {

    /**
     * List Vulnerabilities for a Preset Preview
     *
     * <p>Vulnerability list for a given preset preview.
     *
     * <p>프리셋 미리보기 기준으로 취약점 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/vulnerability-list}
     *
     * <p>{@code 401} - incorrect token
     *
     * @param preview preview payload (미리보기 페이로드)
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 취약점 목록
     */
    @PostMapping("/presets/preview/visualisations/vulnerability-list")
    List<VulnerabilitiyForList> getVulnerabilityListForPresetPreview(
            @RequestBody PresetPayload preview,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Vulnerability Device Ids for Preset Preview
     *
     * <p>Vulnerability device id list for the given preset preview.
     *
     * <p>프리셋 미리보기 기준으로 특정 취약점의 디바이스 ID 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/vulnerability-list/{vuln_id}/devices-ids}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown vulnerability
     *
     * @param vulnId vulnerability id (uuid) (취약점 ID (UUID))
     * @param preview preview payload (미리보기 페이로드)
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 디바이스 ID 목록
     */
    @PostMapping("/presets/preview/visualisations/vulnerability-list/{vuln_id}/devices-ids")
    List<String> getVulnerabilityDevicesForPresetPreview(
            @PathVariable("vuln_id") String vulnId,
            @RequestBody PresetPayload preview,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Vulnerability Orphan Ids for Preset Preview
     *
     * <p>Vulnerability orphan id list for the given preset preview.
     *
     * <p>프리셋 미리보기 기준으로 특정 취약점의 고아(orphan) ID 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/vulnerability-list/{vuln_id}/orphans-ids}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown vulnerability
     *
     * @param vulnId vulnerability id (uuid) (취약점 ID (UUID))
     * @param preview preview payload (미리보기 페이로드)
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 고아 ID 목록
     */
    @PostMapping("/presets/preview/visualisations/vulnerability-list/{vuln_id}/orphans-ids")
    List<String> getVulnerabilityOrphansForPresetPreview(
            @PathVariable("vuln_id") String vulnId,
            @RequestBody PresetPayload preview,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Vulnerabilities for a Preset
     *
     * <p>List the vulnerabilities for a preset. Warning: this endpoint is cached. This means that the
     * returned data is not necessarily the latest. So, be careful when using this endpoint with live data
     * endpoints. The cache is automatically updated, but can also be updated at will thanks to POST
     * /presets/{preset_id}/refreshData.
     *
     * <p>프리셋에 속한 취약점 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/vulnerability-list}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @param format the response format. (응답 형식)
     * @return 취약점 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/vulnerability-list")
    List<VulnerabilitiyForList> getVulnerabilityListForPreset(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "format", required = false) String format);

    /**
     * List Vulnerability Device Ids for a Preset
     *
     * <p>Device id list of a vulnerability for a given preset.
     *
     * <p>프리셋 기준으로 특정 취약점에 해당하는 디바이스 ID 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/vulnerability-list/{vuln_id}/devices-ids}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset or vulnerability not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param vulnId vulnerability id (uuid) (취약점 ID (UUID))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 디바이스 ID 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/vulnerability-list/{vuln_id}/devices-ids")
    List<String> getVulnerabilityDevicesForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("vuln_id") String vulnId,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Vulnerability Orphan Ids for a Preset
     *
     * <p>Orphan id list of a vulnerability for a given preset.
     *
     * <p>프리셋 기준으로 특정 취약점의 고아(orphan) ID 목록을 조회한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/vulnerability-list/{vuln_id}/orphans-ids}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - preset or vulnerability not found
     *
     * @param presetId preset id (uuid) (프리셋 ID (UUID))
     * @param vulnId vulnerability id (uuid) (취약점 ID (UUID))
     * @param from start of the period to query, in ms since January 1, 1970 UTC (조회 기간 시작 시각 (Unix epoch 밀리초, UTC))
     * @param to end of the period to query, in ms since January 1, 1970 UTC (조회 기간 종료 시각 (Unix epoch 밀리초, UTC))
     * @return 고아 ID 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/vulnerability-list/{vuln_id}/orphans-ids")
    List<String> getVulnerabilityOrphansForPreset(
            @PathVariable("preset_id") String presetId,
            @PathVariable("vuln_id") String vulnId,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to);

    /**
     * List Vulnerabilities
     *
     * <p>Vulnerability list.
     *
     * <p>취약점 목록을 조회한다.
     *
     * <p>{@code GET /vulnerabilities}
     *
     * <p>{@code 401} - incorrect token
     *
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 취약점 목록
     */
    @GetMapping("/vulnerabilities")
    List<VulnerabilityDetail> getVulnerabilityList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Vulnerability Details
     *
     * <p>Vulnerability details.
     *
     * <p>취약점 상세를 조회한다.
     *
     * <p>{@code GET /vulnerabilities/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown vulnerability
     *
     * @param id vulnerability id (uuid) (취약점 ID (UUID))
     * @return 취약점 상세
     */
    @GetMapping("/vulnerabilities/{id}")
    List<VulnerabilityDetail> getVulnerability(@PathVariable("id") String id);

    /**
     * Acknowledge Vulnerabilities on Multiple Devices
     *
     * <p>Ack Vulnerability on bulk devices.
     *
     * <p>여러 디바이스의 취약점을 일괄 승인 처리한다.
     *
     * <p>{@code PUT /vulnerability/acknowledge}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - vulnerability not found
     *
     * @param bulkVuln bulk vuln payload (취약점 일괄 처리 페이로드)
     * @return 처리 결과
     */
    @PutMapping("/vulnerability/acknowledge")
    SimpleResponse acknowledgeVulnerabilityOnBulkDevices(@RequestBody AckVulnerabilityForDevices bulkVuln);
}
