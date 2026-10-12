package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.AddExtraFieldBody;
import com.cvGateway.cvGateway.cybervision.model.AddExtraFieldResp;
import com.cvGateway.cvGateway.cybervision.model.CustomNameBody;
import com.cvGateway.cvGateway.cybervision.model.SimpleResponse;
import com.cvGateway.cvGateway.cybervision.model.UpdateExtraFieldBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code CustomProperties}.
 *
 * <p><b>사용자 정의 속성 (Custom Properties)</b>
 * 컴포넌트·디바이스·그룹에 사용자 지정 이름과 임의의 속성을 붙인다. 디바이스는 언제든 삭제될 수 있는 휘발성 객체라 붙인 값이 사라질 수 있다.
 *
 * <p>오퍼레이션 5개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionCustomProperties",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface CustomPropertiesClient {

    /**
     * Set Custom Name for Device or Component
     *
     * <p>Set the custom name for a device or a component. Device are volatile objects in Cyber Vision: expect
     * devices to be deleted at any moment and custom names to be lost.
     *
     * <p>디바이스 또는 컴포넌트에 사용자 지정 이름을 설정한다.
     *
     * <p>디바이스는 휘발성 객체라 언제든 삭제될 수 있고, 그때 지정한 이름도 함께 사라진다.
     *
     * <p>{@code POST /{object}/{id}/label}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found
     *
     * @param object the object (components or devices) where to update extra property (속성을 수정할 대상 종류 (components 또는 devices))
     * @param id component or device id. (컴포넌트 또는 디바이스 ID)
     * @param name component name (컴포넌트 이름)
     * @return 설정 결과
     */
    @PostMapping("/{object}/{id}/label")
    SimpleResponse postCustomName(
            @PathVariable("object") String object,
            @PathVariable("id") String id,
            @RequestBody CustomNameBody name);

    /**
     * Delete Custom Name for Device or Component
     *
     * <p>Delete the custom name for device or component.
     *
     * <p>디바이스 또는 컴포넌트의 사용자 지정 이름을 삭제한다.
     *
     * <p>{@code DELETE /{object}/{id}/label}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     *
     * @param object the object (components or devices) where to delete extra property (속성을 삭제할 대상 종류 (components 또는 devices))
     * @param id component or device id. (컴포넌트 또는 디바이스 ID)
     * @return 삭제 결과
     */
    @DeleteMapping("/{object}/{id}/label")
    SimpleResponse deleteCustomName(
            @PathVariable("object") String object,
            @PathVariable("id") String id);

    /**
     * Add Property to Component, Group or Device
     *
     * <p>Add an extra property to either a component, a group or a device.
     *
     * <p>컴포넌트·그룹·디바이스에 사용자 정의 속성을 추가한다.
     *
     * <p>{@code POST /{object}/{id}/usersProperties}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found
     * <p>{@code 412} - precondition failed. Label should be &lt;= 60 characters and Value &lt;= 180 characters
     *
     * @param object the object (components, groups or devices) where to add extra property (속성을 추가할 대상 종류 (components, groups, devices))
     * @param id the object id (component id or group id) (대상 객체 ID (컴포넌트 ID 또는 그룹 ID))
     * @param property the property to add (추가할 속성)
     * @return 추가 결과
     */
    @PostMapping("/{object}/{id}/usersProperties")
    AddExtraFieldResp postExtraField(
            @PathVariable("object") String object,
            @PathVariable("id") String id,
            @RequestBody AddExtraFieldBody property);

    /**
     * Update Property for Component, Group, Device
     *
     * <p>Update a property to a component, a group or a device. Devices are volatile objects in Cyber Vision:
     * expect devices to be deleted at any moment and extra properties to be lost.
     *
     * <p>컴포넌트·그룹·디바이스의 사용자 정의 속성을 수정한다.
     *
     * <p>디바이스는 휘발성 객체라 언제든 삭제될 수 있고, 그때 추가한 속성도 함께 사라진다.
     *
     * <p>{@code PUT /{object}/{id}/usersProperties/{propertyId}}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found
     * <p>{@code 412} - precondition failed. Label should be &lt;= 60 characters and Value &lt;= 180 characters
     *
     * @param object the object (components, groups or devices) where to update extra property (속성을 수정할 대상 종류 (components, groups, devices))
     * @param id the object id (component id or group id) (대상 객체 ID (컴포넌트 ID 또는 그룹 ID))
     * @param propertyId property id to update (수정할 속성 ID)
     * @param property the property to update (수정할 속성)
     * @return 수정 결과
     */
    @PutMapping("/{object}/{id}/usersProperties/{propertyId}")
    SimpleResponse putExtraField(
            @PathVariable("object") String object,
            @PathVariable("id") String id,
            @PathVariable("propertyId") String propertyId,
            @RequestBody UpdateExtraFieldBody property);

    /**
     * Delete Property from Component, Group or Device
     *
     * <p>Delete a property from a component, a group or a device.
     *
     * <p>컴포넌트·그룹·디바이스에서 사용자 정의 속성을 삭제한다.
     *
     * <p>{@code DELETE /{object}/{id}/usersProperties/{propertyId}}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - not found.
     *
     * @param object the object (components, groups or devices) where to delete extra property (속성을 삭제할 대상 종류 (components, groups, devices))
     * @param id the object id (component id or group id) (대상 객체 ID (컴포넌트 ID 또는 그룹 ID))
     * @param propertyId property id to delete (삭제할 속성 ID)
     * @return 삭제 결과
     */
    @DeleteMapping("/{object}/{id}/usersProperties/{propertyId}")
    SimpleResponse deleteExtraField(
            @PathVariable("object") String object,
            @PathVariable("id") String id,
            @PathVariable("propertyId") String propertyId);
}
