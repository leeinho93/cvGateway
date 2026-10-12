package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.ConfigureActiveDiscoveryBody;
import com.cvGateway.cvGateway.cybervision.model.CreateSensorBody;
import com.cvGateway.cvGateway.cybervision.model.GetSensorPackageBody;
import com.cvGateway.cvGateway.cybervision.model.Sensor;
import com.cvGateway.cvGateway.cybervision.model.SensorCaptureModeFilter;
import com.cvGateway.cvGateway.cybervision.model.SensorConfigureActiveDiscoveryOutput;
import com.cvGateway.cvGateway.cybervision.model.SensorDetails;
import com.cvGateway.cvGateway.cybervision.model.SensorLocationEditBody;
import com.cvGateway.cvGateway.cybervision.model.SensorOverview;
import com.cvGateway.cvGateway.cybervision.model.SensorStats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Sensors}.
 *
 * <p><b>센서 (Sensors)</b>
 * 트래픽을 수집하는 센서의 등록·조회·삭제와 캡처 모드, Snort(IDS), 위치, 능동 탐색 설정을 관리한다.
 *
 * <p>오퍼레이션 12개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionSensors",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface SensorsClient {

    /**
     * List Sensors
     *
     * <p>Sensor list.
     *
     * <p>센서 목록을 조회한다.
     *
     * <p>{@code GET /sensors}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 센서 목록
     */
    @GetMapping("/sensors")
    List<SensorOverview> getSensorList();

    /**
     * Create a New Sensor
     *
     * <p>센서를 등록한다.
     *
     * <p>{@code POST /sensors}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 409} - sensor already exists
     *
     * @param sensor sensor settings (센서 설정)
     * @return 등록 결과
     */
    @PostMapping("/sensors")
    Sensor createSensor(@RequestBody CreateSensorBody sensor);

    /**
     * Update Sensor Active Discovery Configuration
     *
     * <p>센서의 능동 탐색 설정을 변경한다.
     *
     * <p>{@code POST /sensors/active-discovery}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param sensor active discovery configuration parameters along with sensor id (uuid) (센서 ID(UUID)와 능동 탐색 설정 값)
     * @return 설정 결과
     */
    @PostMapping("/sensors/active-discovery")
    SensorConfigureActiveDiscoveryOutput configureActiveDiscovery(@RequestBody ConfigureActiveDiscoveryBody sensor);

    /**
     * Create Sensor Packaging File
     *
     * <p>Create a sensor packaging file.
     *
     * <p>센서 패키징 파일을 생성한다.
     *
     * <p>응답은 바이너리 파일이다.
     *
     * <p>{@code POST /sensors/package}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param sensor sensor identification (센서 식별 정보)
     * @return 패키징 파일 바이너리
     */
    @PostMapping("/sensors/package")
    byte[] getSensorPackaging(@RequestBody GetSensorPackageBody sensor);

    /**
     * Rename Sensor
     *
     * <p>Rename a sensor.
     *
     * <p>센서 이름을 변경한다.
     *
     * <p>{@code POST /sensors/rename}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @param name sensor name (센서 이름)
     * @return 변경 결과
     */
    @PostMapping("/sensors/rename")
    String renameSensor(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "name", required = true) String name);

    /**
     * Get Sensor Details
     *
     * <p>Sensor details.
     *
     * <p>센서 상세를 조회한다.
     *
     * <p>{@code GET /sensors/{id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @return 센서 상세
     */
    @GetMapping("/sensors/{id}")
    SensorDetails getSensor(@PathVariable("id") String id);

    /**
     * Delete a Sensor
     *
     * <p>If the sensor is managed by the sensor management extension, the center tries to uninstall the IOx
     * application from the sensor.
     *
     * <p>센서를 삭제한다.
     *
     * <p>센서 관리 확장으로 관리되는 센서라면 Center 가 해당 센서에서 IOx 애플리케이션 제거를 시도한다.
     *
     * <p>{@code DELETE /sensors/{id}}
     *
     * <p>{@code 204} - sensor removal successful
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     */
    @DeleteMapping("/sensors/{id}")
    void removeSensor(@PathVariable("id") String id);

    /**
     * Update Sensor Capture Mode
     *
     * <p>센서의 캡처 모드를 변경한다.
     *
     * <p>{@code POST /sensors/{id}/capture-mode}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @param sensorCaptureModeFilter valid values for filter are all, industrial_only, optimal and any custom mode for example not arp (filter 허용값은 all, industrial_only, optimal 및 임의의 커스텀 모드(예: not arp))
     * @return 변경 결과
     */
    @PostMapping("/sensors/{id}/capture-mode")
    String updateSensorCaptureModeFilter(
            @PathVariable("id") String id,
            @RequestBody SensorCaptureModeFilter sensorCaptureModeFilter);

    /**
     * Update a sensor's location
     *
     * <p>Update a sensor's location using a JSON body with `latitude` (range -90 to 90) and `longitude`
     * (range -180 to 180), both expressed in decimal degrees in the WGS84 coordinate system.
     *
     * <p>센서의 위치 정보를 수정한다.
     *
     * <p>WGS84 십진 도 단위로 latitude(-90~90), longitude(-180~180) 를 전달한다.
     *
     * <p>{@code PUT /sensors/{id}/location}
     *
     * <p>{@code 204} - sensor location update successful (empty response)
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @param coordinates JSON body with `latitude` and `longitude` in decimal degrees (WGS84) (WGS84 십진 도 단위의 latitude/longitude 를 담은 JSON 본문)
     */
    @PutMapping("/sensors/{id}/location")
    void locationEdit(
            @PathVariable("id") String id,
            @RequestBody SensorLocationEditBody coordinates);

    /**
     * Delete a sensor's location
     *
     * <p>Remove the stored location from the sensor so it no longer has latitude/longitude coordinates.
     *
     * <p>센서에 저장된 위치 정보를 삭제한다.
     *
     * <p>{@code DELETE /sensors/{id}/location}
     *
     * <p>{@code 204} - sensor location delete successful (empty response)
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     */
    @DeleteMapping("/sensors/{id}/location")
    void locationDelete(@PathVariable("id") String id);

    /**
     * Enable or Disable Snort on Sensor
     *
     * <p>Enable or Disable Snort on a sensor.
     *
     * <p>센서의 Snort(IDS)를 활성화하거나 비활성화한다.
     *
     * <p>{@code POST /sensors/{id}/snort/{action}}
     *
     * <p>{@code 204} - snort enabled or disabled successfully
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - sensor not found
     * <p>{@code 500} - unexpected error
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @param action Enable or Disable Snort on a sensor. (센서의 Snort 활성/비활성)
     */
    @PostMapping("/sensors/{id}/snort/{action}")
    void enableDisableIDS(
            @PathVariable("id") String id,
            @PathVariable("action") String action);

    /**
     * Get Sensor Statistics Data
     *
     * <p>센서의 통계 데이터를 조회한다.
     *
     * <p>{@code GET /sensors/{id}/stats}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     * <p>{@code 500} - unexpected error
     *
     * @param id sensor id (uuid) (센서 ID (UUID))
     * @param p time period (조회 기간)
     * @return 센서 통계
     */
    @GetMapping("/sensors/{id}/stats")
    SensorStats getSensorStats(
            @PathVariable("id") String id,
            @RequestParam(name = "p", required = true) String p);
}
