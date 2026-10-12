package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.ExternalCommunication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code ExternalCommunication}.
 *
 * <p><b>외부 통신 (External Communication)</b>
 * 디바이스 또는 컴포넌트가 외부망과 주고받은 통신 내역.
 *
 * <p>오퍼레이션 1개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionExternalCommunication",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ExternalCommunicationClient {

    /**
     * Get External Communications for Device/Component
     *
     * <p>Get externalCommunications for device or component.
     *
     * <p>디바이스 또는 컴포넌트의 외부 통신 내역을 조회한다.
     *
     * <p>{@code GET /{type}/{id}/externalCommunications}
     *
     * <p>{@code 400} - Bad request (parameters in wrong format)
     * <p>{@code 401} - Incorrect token
     * <p>{@code 404} - Unknown device id or component id
     * <p>{@code 500} - Internal Server Error
     *
     * @param type the type of the network node (네트워크 노드 유형)
     * @param id uuid of the device or the component. (디바이스 또는 컴포넌트의 UUID)
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param sort the fields to sort. Example: externalIP:desc|asc,internalIP:desc|asc,serverPort:desc|asc,hostname:desc|asc,protocol:desc|asc,receivedByDevice:desc|asc,sentByDevice:desc|asc,lastSeen:desc|asc (정렬할 필드. 예: externalIP:desc|asc,internalIP:desc|asc,serverPort:desc|asc,lastSeen:desc|asc)
     * @param filter the fields to filter. Example: externalIP:val,internalIP:val,serverPort:val,hostname:val,protocol:val,direction:val (필터링할 필드. 예: externalIP:val,internalIP:val,serverPort:val,hostname:val,protocol:val,direction:val)
     * @return 외부 통신 목록
     */
    @GetMapping("/{type}/{id}/externalCommunications")
    ExternalCommunication getExternalCommunications(
            @PathVariable("type") String type,
            @PathVariable("id") String id,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);
}
