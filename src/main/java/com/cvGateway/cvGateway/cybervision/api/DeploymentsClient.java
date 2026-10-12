package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;
import java.util.Map;

import com.cvGateway.cvGateway.cybervision.model.DeploymentJWTParams;
import com.cvGateway.cvGateway.cybervision.model.DeploymentKeyParams;
import com.cvGateway.cvGateway.cybervision.model.DeploymentKeyResponse;
import com.cvGateway.cvGateway.cybervision.model.DetailedDeploymentJWTParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Deployments}.
 *
 * <p><b>배포 (Deployments)</b>
 * 센서 배포용 키와 JWT 토큰을 관리한다.
 *
 * <p>오퍼레이션 6개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionDeployments",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface DeploymentsClient {

    /**
     * List Deployments
     *
     * <p>Deployment list.
     *
     * <p>배포 목록을 조회한다.
     *
     * <p>{@code GET /deployments}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 배포 목록
     */
    @GetMapping("/deployments")
    List<DeploymentKeyResponse> getDeploymentList();

    /**
     * Create Deployment
     *
     * <p>Post deployment.
     *
     * <p>배포를 생성한다.
     *
     * <p>{@code POST /deployments}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 409} - another deployment with the given name already exists
     *
     * @param deployment deployment payload (배포 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/deployments")
    DeploymentKeyResponse postDeployment(@RequestBody DeploymentKeyParams deployment);

    /**
     * Generate Deployment JWT
     *
     * <p>Generate deployment JWTs. The response is a JSON object where each key is an input serial number and
     * each value is the generated JWT token for that serial number.
     *
     * <p>배포용 JWT 를 발급한다.
     *
     * <p>응답은 시리얼 번호를 키로, 해당 시리얼에 발급된 JWT 를 값으로 갖는 JSON 객체다.
     *
     * <p>{@code POST /deployments/jwt}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - deployment with the given name does not exist
     *
     * @param params deployment payload (배포 페이로드)
     * @return 시리얼 번호별 JWT
     */
    @PostMapping("/deployments/jwt")
    Map<String, Object> generateDeploymentJWT(@RequestBody DeploymentJWTParams params);

    /**
     * Generate Detailed Deployment JWT
     *
     * <p>상세 정보를 포함한 배포용 JWT 를 발급한다.
     *
     * <p>{@code POST /deployments/jwt/detailed}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - deployment with the given name does not exist
     *
     * @param params deployment payload (배포 페이로드)
     * @return JWT 목록
     */
    @PostMapping("/deployments/jwt/detailed")
    List<String> generateDetailedDeploymentJWT(@RequestBody DetailedDeploymentJWTParams params);

    /**
     * Update Deployment
     *
     * <p>Put deployment.
     *
     * <p>배포를 수정한다.
     *
     * <p>{@code PUT /deployments/{key_id}}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - deployment with the given ID does not exist
     * <p>{@code 409} - another deployment with the given name already exists
     *
     * @param keyId id of the deployment to edit (수정할 배포 ID)
     * @param deployment deployment payload (배포 페이로드)
     * @return 수정 결과
     */
    @PutMapping("/deployments/{key_id}")
    String putDeployment(
            @PathVariable("key_id") String keyId,
            @RequestBody DeploymentKeyParams deployment);

    /**
     * Delete Deployment
     *
     * <p>배포를 삭제한다.
     *
     * <p>{@code DELETE /deployments/{key_id}}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - deployment with the given ID does not exist
     *
     * @param keyId id of the deployment to edit (수정할 배포 ID)
     * @return 삭제 결과
     */
    @DeleteMapping("/deployments/{key_id}")
    String deleteDeployment(@PathVariable("key_id") String keyId);
}
