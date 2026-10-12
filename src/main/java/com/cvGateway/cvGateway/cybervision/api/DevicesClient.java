package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Activity;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Device;
import com.cvGateway.cvGateway.cybervision.model.DeviceCredential;
import com.cvGateway.cvGateway.cybervision.model.DeviceRiskScoreStruct;
import com.cvGateway.cvGateway.cybervision.model.JsonVulnerabilityFullInfos;
import com.cvGateway.cvGateway.cybervision.model.JsonVulnerabilityFullInfosForDevice;
import com.cvGateway.cvGateway.cybervision.model.NetworkNodes;
import com.cvGateway.cvGateway.cybervision.model.PresetPayload;
import com.cvGateway.cvGateway.cybervision.model.Variable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Devices}.
 *
 * <p><b>디바이스 (Devices)</b>
 * 여러 컴포넌트를 묶은 논리적 장비 단위. 액티비티·취약점·변수·자격증명 조회를 제공한다.
 *
 * <p>오퍼레이션 11개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionDevices",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface DevicesClient {

    /**
     * List All Devices
     *
     * <p>List of all devices.
     *
     * <p>모든 디바이스 목록을 조회한다.
     *
     * <p>{@code GET /devices}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param from last_update start date/time (last_update 기준 시작 일시)
     * @param to end date/time (종료 일시)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param vulnerabilities include each device vulnerabilities (디바이스별 취약점을 함께 포함할지 여부)
     * @return 디바이스 목록
     */
    @GetMapping("/devices")
    Device getAll(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "vulnerabilities", required = false) Boolean vulnerabilities);

    /**
     * Get Vulnerability Details for a Device
     *
     * <p>Vulnerability detail for a device.
     *
     * <p>디바이스의 특정 취약점 상세를 조회한다.
     *
     * <p>{@code GET /devices/{device_id}/vulnerabilities/{vulnerability_id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device or vulnerability not found
     *
     * @param deviceId device id (uuid) (디바이스 ID (UUID))
     * @param vulnerabilityId vulnerability id (uuid) (취약점 ID (UUID))
     * @return 취약점 상세
     */
    @GetMapping("/devices/{device_id}/vulnerabilities/{vulnerability_id}")
    JsonVulnerabilityFullInfosForDevice getDeviceVulnerabilityDetail(
            @PathVariable("device_id") String deviceId,
            @PathVariable("vulnerability_id") String vulnerabilityId);

    /**
     * Get Device Details
     *
     * <p>Get information for specific devices.
     *
     * <p>디바이스 상세를 조회한다.
     *
     * <p>{@code GET /devices/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id a uuid sequence with ';' as separator (none if there is only one id). (세미콜론으로 구분한 UUID 목록. ID 가 하나면 구분자는 생략한다)
     * @return 디바이스 상세
     */
    @GetMapping("/devices/{id}")
    Device getDeviceDetails(@PathVariable("id") String id);

    /**
     * List Device Activities
     *
     * <p>Get activities for specific devices.
     *
     * <p>디바이스의 액티비티 목록을 조회한다.
     *
     * <p>{@code GET /devices/{id}/activities}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id a uuid sequence with ';' as separator (none if there is only one id). (세미콜론으로 구분한 UUID 목록. ID 가 하나면 구분자는 생략한다)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param sort the fields to sort. Example: field1,field2:desc (정렬할 필드. 예: field1,field2:desc)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 액티비티 목록
     */
    @GetMapping("/devices/{id}/activities")
    Activity getDeviceActivities(
            @PathVariable("id") String id,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Device Activity Tags
     *
     * <p>디바이스 액티비티의 태그 목록을 조회한다.
     *
     * <p>{@code GET /devices/{id}/activities/tags}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id a uuid sequence with ';' as separator (none if there is only one id). (세미콜론으로 구분한 UUID 목록. ID 가 하나면 구분자는 생략한다)
     * @return 태그 목록
     */
    @GetMapping("/devices/{id}/activities/tags")
    DetailedTag listDeviceActivitiesTags(@PathVariable("id") String id);

    /**
     * List Credentials for a Device
     *
     * <p>Credential list of a device.
     *
     * <p>디바이스의 자격증명 목록을 조회한다.
     *
     * <p>{@code GET /devices/{id}/credentials}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id device id (uuid) (디바이스 ID (UUID))
     * @return 자격증명 목록
     */
    @GetMapping("/devices/{id}/credentials")
    List<DeviceCredential> getDeviceCredentialList(@PathVariable("id") String id);

    /**
     * Get Device Risk Score
     *
     * <p>Risk score of a device.
     *
     * <p>디바이스의 리스크 스코어를 조회한다.
     *
     * <p>{@code GET /devices/{id}/riskScore}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id device id (uuid) (디바이스 ID (UUID))
     * @return 리스크 스코어
     */
    @GetMapping("/devices/{id}/riskScore")
    DeviceRiskScoreStruct getDeviceRiskScore(@PathVariable("id") String id);

    /**
     * List Variables for a Device
     *
     * <p>Variable list of a device.
     *
     * <p>디바이스의 변수 목록을 조회한다.
     *
     * <p>{@code GET /devices/{id}/variables}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id device id (uuid) (디바이스 ID (UUID))
     * @return 변수 목록
     */
    @GetMapping("/devices/{id}/variables")
    Variable getDeviceVariableList(@PathVariable("id") String id);

    /**
     * List Vulnerabilities for a Device
     *
     * <p>Vulnerability list of a device.
     *
     * <p>디바이스의 취약점 목록을 조회한다.
     *
     * <p>{@code GET /devices/{id}/vulnerabilities}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param id device id (uuid) (디바이스 ID (UUID))
     * @return 취약점 목록
     */
    @GetMapping("/devices/{id}/vulnerabilities")
    List<JsonVulnerabilityFullInfos> getDeviceVulnerabilityList(@PathVariable("id") String id);

    /**
     * List Devices for Preset Preview
     *
     * <p>Create a list of devices.
     *
     * <p>프리셋 미리보기 기준으로 디바이스 목록을 조회한다.
     *
     * <p>{@code POST /presets/preview/visualisations/networknode-list}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param from start date/time (시작 일시)
     * @param to end date/time (종료 일시)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param format response format (응답 형식)
     * @param fields comma separated list of fields to fetch (조회할 필드 목록 (쉼표 구분))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @param preset preset payload (프리셋 페이로드)
     * @return 디바이스 목록
     */
    @PostMapping("/presets/preview/visualisations/networknode-list")
    NetworkNodes getDeviceListPreview(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "fields", required = false) String fields,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestBody PresetPayload preset);

    /**
     * List Devices
     *
     * <p>List network nodes (devices and components) for a preset. Warning: this endpoint is cached. This
     * means that the returned data is not necessarily the latest. So, be careful when using this endpoint
     * with live data endpoints. The cache is automatically updated, but can also be updated at will thanks
     * to POST /presets/{preset_id}/refreshData.
     *
     * <p>프리셋의 네트워크 노드(디바이스·컴포넌트) 목록을 조회한다.
     *
     * <p>캐시된 응답이라 최신이 아닐 수 있다. 즉시 갱신이 필요하면 POST /presets/{preset_id}/refreshData 를 먼저 호출한다.
     *
     * <p>{@code GET /presets/{preset_id}/visualisations/networknode-list}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - device not found
     *
     * @param presetId preset_id (uuid) (프리셋 ID (UUID))
     * @param from start date/time (시작 일시)
     * @param to end date/time (종료 일시)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param format response format (응답 형식)
     * @param fields comma separated list of fields to fetch (조회할 필드 목록 (쉼표 구분))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 네트워크 노드 목록
     */
    @GetMapping("/presets/{preset_id}/visualisations/networknode-list")
    NetworkNodes getDeviceList(
            @PathVariable("preset_id") String presetId,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "format", required = false) String format,
            @RequestParam(name = "fields", required = false) String fields,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
