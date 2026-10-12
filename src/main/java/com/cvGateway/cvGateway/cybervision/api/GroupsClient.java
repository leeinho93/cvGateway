package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Group;
import com.cvGateway.cvGateway.cybervision.model.GroupDetail;
import com.cvGateway.cvGateway.cybervision.model.GroupPayload;
import com.cvGateway.cvGateway.cybervision.model.PartialUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Groups}.
 *
 * <p><b>그룹 (Groups)</b>
 * 컴포넌트·디바이스를 묶는 사용자 정의 그룹.
 *
 * <p>오퍼레이션 6개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionGroups",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface GroupsClient {

    /**
     * List Groups
     *
     * <p>그룹 목록을 조회한다.
     *
     * <p>{@code GET /groups}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 그룹 목록
     */
    @GetMapping("/groups")
    List<Group> listGroups();

    /**
     * Create a Group
     *
     * <p>그룹을 생성한다.
     *
     * <p>{@code POST /groups}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 412} - precondition failed on some field of the body
     *
     * @param group group payload (그룹 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/groups")
    GroupDetail createGroup(@RequestBody GroupPayload group);

    /**
     * Get Details of One or Many Groups
     *
     * <p>하나 또는 여러 그룹의 상세를 조회한다.
     *
     * <p>{@code GET /groups/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - at least one group not found
     *
     * @param id an uuid sequence with semicolon as separator. Example: cbcbed07-1ed4-4fa6-817c-f3c1702fd187;bb9d9840-82b0-42b7-9e03-13a3832fb363 (세미콜론으로 구분한 UUID 목록)
     * @return 그룹 상세 목록
     */
    @GetMapping("/groups/{id}")
    List<GroupDetail> getGroup(@PathVariable("id") String id);

    /**
     * Update a Group
     *
     * <p>그룹을 수정한다.
     *
     * <p>{@code PUT /groups/{id}}
     *
     * <p>{@code 400} - bad request. It generally means that the payload is not well formed or that some important information are missing
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - group not found
     * <p>{@code 409} - There is another group that already has the label we want to set
     * <p>{@code 412} - precondition failed on some field of the body
     * <p>{@code 422} - unprocessable entity. Invalid parent hierarchy
     * <p>{@code 501} - not implemented. The group was not created locally
     *
     * @param id group id (uuid) (그룹 ID (UUID))
     * @param group group payload (그룹 페이로드)
     * @return 수정 결과
     */
    @PutMapping("/groups/{id}")
    GroupDetail updateGroup(
            @PathVariable("id") String id,
            @RequestBody GroupPayload group);

    /**
     * Update One Property of the Given Group
     *
     * <p>For the moment, add and remove on components are implemented. For more information, see
     * https://tools.ietf.org/html/rfc6902
     *
     * <p>그룹의 속성 하나를 부분 수정한다.
     *
     * <p>현재는 components 경로에 대한 add/remove 만 구현돼 있다. 형식은 RFC 6902(JSON Patch)를 따른다.
     *
     * <p>{@code PATCH /groups/{id}}
     *
     * <p>{@code 400} - bad request. The "op" doesn't exist.
     * <p>{@code 401} - incorrect token
     * <p>{@code 403} - forbidden. The "op" - "Path" association is not allowed.
     * <p>{@code 404} - No group found.
     * <p>{@code 422} - Unprocessable Entity. Invalid parent hierarchy
     * <p>{@code 501} - Not implemented. The group was not created locally.
     *
     * @param id group id (uuid) (그룹 ID (UUID))
     * @param group group payload. "op" can be "add" or "remove" on path "/components", "/devices" or "/groups". Many component ids are allowed in "value" field. (그룹 페이로드. path 가 "/components", "/devices", "/groups" 일 때 op 는 "add" 또는 "remove" 이며, value 에 여러 컴포넌트 ID 를 넣을 수 있다)
     * @return 수정 결과
     */
    @PatchMapping("/groups/{id}")
    GroupDetail patchGroup(
            @PathVariable("id") String id,
            @RequestBody PartialUpdate group);

    /**
     * Delete a Group
     *
     * <p>그룹을 삭제한다.
     *
     * <p>{@code DELETE /groups/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - group not found
     *
     * @param id group id (uuid) (그룹 ID (UUID))
     * @return 삭제 결과
     */
    @DeleteMapping("/groups/{id}")
    String deleteGroup(@PathVariable("id") String id);
}
