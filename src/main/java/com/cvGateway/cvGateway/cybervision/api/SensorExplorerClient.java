package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.CreateFolder;
import com.cvGateway.cvGateway.cybervision.model.LightFolder;
import com.cvGateway.cvGateway.cybervision.model.SensorExplorerEntry;
import com.cvGateway.cvGateway.cybervision.model.SensorExplorerMoveTarget;
import com.cvGateway.cvGateway.cybervision.model.SensorExplorerSelection;
import com.cvGateway.cvGateway.cybervision.model.SensorFolder;
import com.cvGateway.cvGateway.cybervision.model.SimpleResponse;
import com.cvGateway.cvGateway.cybervision.model.UpdateFolder;
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
 * Cisco Cyber Vision Classic API - {@code SensorExplorer}.
 *
 * <p><b>센서 탐색기 (Sensor Explorer)</b>
 * 센서를 폴더 트리로 관리한다. 폴더 CRUD와 센서 이동, 페이지네이션 조회를 제공한다.
 *
 * <p>오퍼레이션 9개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionSensorExplorer",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface SensorExplorerClient {

    /**
     * List Sensors and Folders
     *
     * <p>Returns paginated sensors and folders matching filters and sort order.
     *
     * <p>센서와 폴더를 필터·정렬·페이지 단위로 조회한다.
     *
     * <p>{@code GET /admin/sensorExplorer}
     *
     * <p>{@code 400} - filters or sorts are not in expected format
     * <p>{@code 401} - incorrect token
     *
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 센서·폴더 목록
     */
    @GetMapping("/admin/sensorExplorer")
    SensorExplorerEntry getSensorsAndFoldersList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * List Folder IDs and Labels
     *
     * <p>Returns lightweight folder entries (containing only id and label).
     *
     * <p>폴더의 ID 와 라벨만 담은 경량 목록을 조회한다.
     *
     * <p>{@code GET /admin/sensorExplorer/folders}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 폴더 목록
     */
    @GetMapping("/admin/sensorExplorer/folders")
    LightFolder getLightFolderList();

    /**
     * Create Folder
     *
     * <p>Creates a new folder. A folder with the same name must not already exist.
     *
     * <p>폴더를 생성한다.
     *
     * <p>같은 이름의 폴더가 이미 있으면 실패한다.
     *
     * <p>{@code POST /admin/sensorExplorer/folders}
     *
     * <p>{@code 400} - the payload was not properly formatted (invalid json)
     * <p>{@code 401} - incorrect token
     * <p>{@code 409} - a folder with the same name already exists
     *
     * @param folder folder to create (생성할 폴더)
     * @return 생성 결과
     */
    @PostMapping("/admin/sensorExplorer/folders")
    SensorFolder createFolder(@RequestBody CreateFolder folder);

    /**
     * List Sensors in Folder
     *
     * <p>Returns paginated sensors from the specified folder with filters and sorting.
     *
     * <p>지정한 폴더의 센서를 필터·정렬·페이지 단위로 조회한다.
     *
     * <p>{@code GET /admin/sensorExplorer/folders/{folderID}}
     *
     * <p>{@code 401} - incorrect token
     *
     * @param folderID folder id (폴더 ID)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 센서 목록
     */
    @GetMapping("/admin/sensorExplorer/folders/{folderID}")
    SensorExplorerEntry getSensorListByFolderId(
            @PathVariable("folderID") String folderID,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Update Folder Details
     *
     * <p>Updates an existing folder using the provided folder payload.
     *
     * <p>폴더 정보를 수정한다.
     *
     * <p>{@code PUT /admin/sensorExplorer/folders/{folderID}}
     *
     * <p>{@code 400} - folder id is not an uuid or payload was not properly formatted (invalid json)
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - folder id does not exist
     * <p>{@code 409} - the given folder label does already exist
     *
     * @param folderID folder id (폴더 ID)
     * @param folder updating folder (수정할 폴더)
     * @return 수정 결과
     */
    @PutMapping("/admin/sensorExplorer/folders/{folderID}")
    SensorFolder updateFolder(
            @PathVariable("folderID") String folderID,
            @RequestBody UpdateFolder folder);

    /**
     * Delete a Folder
     *
     * <p>Deletes the folder given by the folderID. The folder's sensors are moved to the root folder.
     *
     * <p>폴더를 삭제한다.
     *
     * <p>폴더에 속해 있던 센서는 루트 폴더로 옮겨진다.
     *
     * <p>{@code DELETE /admin/sensorExplorer/folders/{folderID}}
     *
     * <p>{@code 400} - folder id is not an uuid
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - folder id does not exist
     *
     * @param folderID folder id (폴더 ID)
     * @return 삭제 결과
     */
    @DeleteMapping("/admin/sensorExplorer/folders/{folderID}")
    SimpleResponse deleteFolder(@PathVariable("folderID") String folderID);

    /**
     * Get Folder Properties
     *
     * <p>Returns the full serialized folder for the provided folder id.
     *
     * <p>폴더의 전체 속성을 조회한다.
     *
     * <p>{@code GET /admin/sensorExplorer/folders/{folderID}/properties}
     *
     * <p>{@code 400} - id is not an uuid
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - id does not exist
     *
     * @param folderID folder id (폴더 ID)
     * @return 폴더 상세
     */
    @GetMapping("/admin/sensorExplorer/folders/{folderID}/properties")
    SensorFolder getFolderProperties(@PathVariable("folderID") String folderID);

    /**
     * List Selected Sensors and Folders
     *
     * <p>Returns paginated selected sensors and folders from the request body.
     *
     * <p>요청 본문으로 지정한 센서·폴더를 페이지 단위로 조회한다.
     *
     * <p>{@code POST /admin/sensorExplorer/selection}
     *
     * <p>{@code 400} - payload not properly formatted
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - given sensor or folder id does not exit
     * <p>{@code 412} - given sensor or folder type is different of "folder" or "sensor"
     *
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param selection sensor and folder selection (센서·폴더 선택 정보)
     * @return 센서·폴더 목록
     */
    @PostMapping("/admin/sensorExplorer/selection")
    SensorExplorerEntry getSelectedFoldersAndSensors(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestBody List<SensorExplorerSelection> selection);

    /**
     * Move a Sensor or Folder
     *
     * <p>Moves a sensor or the sensors of a folder to the target folder. If the target folder is not
     * provided, the sensor(s) will be moved to the "Root" folder.
     *
     * <p>센서 또는 폴더를 다른 폴더로 옮긴다.
     *
     * <p>대상 폴더를 지정하지 않으면 "Root" 폴더로 이동한다. 폴더를 지정하면 그 안의 센서가 모두 함께 이동한다.
     *
     * <p>{@code POST /admin/sensorExplorer/selection/moveTo}
     *
     * <p>{@code 400} - payload not properly formatted
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - given sensor or folder id does not exit
     * <p>{@code 412} - given sensor or folder type is different of "folder" or "sensor"
     *
     * @param sensors id can be either a sensor or a folder, that needs to be moved. valid values for type are sensor and folder. If folder is provided, all sensors within the folder will be moved to the target folder. (이동할 대상. id 는 센서 또는 폴더이며 type 은 sensor 또는 folder 다. 폴더를 지정하면 폴더 안의 모든 센서가 대상 폴더로 이동한다)
     * @param target ID of the folder where the sensor/folder should be moved. If this value is not provided, the sensor/folder will be moved to the "Root" folder by default. (이동 대상 폴더 ID. 지정하지 않으면 "Root" 폴더로 이동한다)
     * @return 이동 결과
     */
    @PostMapping("/admin/sensorExplorer/selection/moveTo")
    List<String> moveSensorsToFolder(
            @RequestBody List<SensorExplorerMoveTarget> sensors,
            @RequestParam(name = "target", required = false) String target);
}
