package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.SensorTemplate;
import com.cvGateway.cvGateway.cybervision.model.SensorTemplateAddSensorsPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code SensorTemplates}.
 *
 * <p><b>센서 템플릿 (Sensor Templates)</b>
 * 여러 센서에 동일 설정을 일괄 적용하기 위한 템플릿.
 *
 * <p>오퍼레이션 2개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionSensorTemplates",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface SensorTemplatesClient {

    /**
     * List the Sensor Templates
     *
     * <p>Get the list of sensor templates.
     *
     * <p>센서 템플릿 목록을 조회한다.
     *
     * <p>{@code GET /admin/sensorTemplates}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 템플릿 목록
     */
    @GetMapping("/admin/sensorTemplates")
    SensorTemplate getSensorTemplatesList();

    /**
     * Update Sensor Template
     *
     * <p>Update a sensor template.
     *
     * <p>센서 템플릿에 센서를 추가한다.
     *
     * <p>{@code POST /admin/sensorTemplates/{template_id}/addSensors}
     *
     * <p>{@code 400} - payload not properly formatted
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - given sensor or template id does not exit
     *
     * @param templateId uuid of the template to which the sensors should be assigned. use "default" to assign sensors to the default template. (센서를 할당할 템플릿 UUID. 기본 템플릿에 할당하려면 "default" 를 쓴다)
     * @param sensorIds sensors to update template (템플릿에 할당할 센서 목록)
     * @return 수정 결과
     */
    @PostMapping("/admin/sensorTemplates/{template_id}/addSensors")
    SensorTemplate addSensorsToSensorTemplate(
            @PathVariable("template_id") String templateId,
            @RequestBody SensorTemplateAddSensorsPayload sensorIds);
}
