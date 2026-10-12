package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.ActiveDiscoveryPolicy;
import com.cvGateway.cvGateway.cybervision.model.ActiveDiscoveryProfile;
import com.cvGateway.cvGateway.cybervision.model.ActiveDiscoveryScanConsolidatedReport;
import com.cvGateway.cvGateway.cybervision.model.SimpleResponse;
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
 * Cisco Cyber Vision Classic API - {@code ActiveDiscovery}.
 *
 * <p><b>능동 탐색 (Active Discovery)</b>
 * 센서가 대상 네트워크에 직접 질의를 보내 자산을 식별하는 기능. 스캔 대상·주기를 정의하는 프로필과, 어떤 프로토콜로 질의할지 정의하는 정책으로 구성된다.
 *
 * <p>오퍼레이션 14개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionActiveDiscovery",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ActiveDiscoveryClient {

    /**
     * List Active Discovery Profiles
     *
     * <p>능동 탐색 프로필 목록을 조회한다.
     *
     * <p>{@code GET /activeDiscovery/profile}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 프로필 목록
     */
    @GetMapping("/activeDiscovery/profile")
    List<ActiveDiscoveryProfile> listProfile();

    /**
     * Create an Active Discovery Profile
     *
     * <p>능동 탐색 프로필을 생성한다.
     *
     * <p>{@code POST /activeDiscovery/profile}
     *
     * <p>{@code 400} - invalid payload
     * <p>{@code 401} - incorrect token
     *
     * @param profile profile payload (프로필 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/activeDiscovery/profile")
    SimpleResponse createProfile(@RequestBody ActiveDiscoveryProfile profile);

    /**
     * Get Active Discovery Profile by Id
     *
     * <p>ID 로 능동 탐색 프로필을 조회한다.
     *
     * <p>{@code GET /activeDiscovery/profile/{profile_id}}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 프로필 목록
     */
    @GetMapping("/activeDiscovery/profile/{profile_id}")
    List<ActiveDiscoveryProfile> getProfile(@PathVariable("profile_id") String profileId);

    /**
     * Edit an Active Discovery Profile
     *
     * <p>능동 탐색 프로필을 수정한다.
     *
     * <p>{@code PATCH /activeDiscovery/profile/{profile_id}}
     *
     * <p>{@code 400} - invalid profile id or invalid payload
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @param profile profile payload (프로필 페이로드)
     * @return 수정 결과
     */
    @PatchMapping("/activeDiscovery/profile/{profile_id}")
    SimpleResponse editProfile(
            @PathVariable("profile_id") String profileId,
            @RequestBody ActiveDiscoveryProfile profile);

    /**
     * Delete an Active Discovery Profile
     *
     * <p>능동 탐색 프로필을 삭제한다.
     *
     * <p>{@code DELETE /activeDiscovery/profile/{profile_id}}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 삭제 결과
     */
    @DeleteMapping("/activeDiscovery/profile/{profile_id}")
    SimpleResponse deleteProfile(@PathVariable("profile_id") String profileId);

    /**
     * Get Profile Last Scan Result
     *
     * <p>Get the last scan result of a profile. The scan can be either completed or ongoing.
     *
     * <p>프로필의 마지막 스캔 결과를 조회한다.
     *
     * <p>완료된 스캔뿐 아니라 진행 중인 스캔도 반환될 수 있다.
     *
     * <p>{@code GET /activeDiscovery/profile/{profile_id}/lastScan}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 스캔 결과 리포트
     */
    @GetMapping("/activeDiscovery/profile/{profile_id}/lastScan")
    List<ActiveDiscoveryScanConsolidatedReport> getProfileScans(@PathVariable("profile_id") String profileId);

    /**
     * Run Active Discovery Profile Once
     *
     * <p>Run once an active discovery profile.
     *
     * <p>능동 탐색 프로필을 즉시 1회 실행한다.
     *
     * <p>{@code POST /activeDiscovery/profile/{profile_id}/runOnce}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 실행 요청 결과
     */
    @PostMapping("/activeDiscovery/profile/{profile_id}/runOnce")
    SimpleResponse runOnceProfile(@PathVariable("profile_id") String profileId);

    /**
     * Set Active Discovery Profile State to SCHEDULED
     *
     * <p>프로필 상태를 SCHEDULED 로 바꿔 예약 실행을 시작한다.
     *
     * <p>{@code POST /activeDiscovery/profile/{profile_id}/start}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 처리 결과
     */
    @PostMapping("/activeDiscovery/profile/{profile_id}/start")
    SimpleResponse startSchedulingProfile(@PathVariable("profile_id") String profileId);

    /**
     * Set Active Discovery Profile State to NOT_SCHEDULED
     *
     * <p>프로필 상태를 NOT_SCHEDULED 로 바꿔 예약 실행을 중단한다.
     *
     * <p>{@code POST /activeDiscovery/profile/{profile_id}/stop}
     *
     * <p>{@code 400} - invalid profile id
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown profile id
     *
     * @param profileId profile id (uuid) (프로필 ID (UUID))
     * @return 처리 결과
     */
    @PostMapping("/activeDiscovery/profile/{profile_id}/stop")
    SimpleResponse stopSchedulingProfile(@PathVariable("profile_id") String profileId);

    /**
     * Get All the Active Discovery Policies
     *
     * <p>능동 탐색 정책 전체를 조회한다.
     *
     * <p>{@code GET /presets/activeDiscovery/policy}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 정책 목록
     */
    @GetMapping("/presets/activeDiscovery/policy")
    List<ActiveDiscoveryPolicy> getPolicies();

    /**
     * Create an Active Discovery Policy
     *
     * <p>능동 탐색 정책을 생성한다.
     *
     * <p>{@code POST /presets/activeDiscovery/policy}
     *
     * <p>{@code 401} - incorrect token
     *
     * @param policy policy payload (정책 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/presets/activeDiscovery/policy")
    SimpleResponse createPolicy(@RequestBody ActiveDiscoveryPolicy policy);

    /**
     * Get a Specific Active Discovery Policy
     *
     * <p>지정한 능동 탐색 정책을 조회한다.
     *
     * <p>{@code GET /presets/activeDiscovery/policy/{policy_id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown policy id
     *
     * @param policyId policy id (uuid) (정책 ID (UUID))
     * @return 정책 상세
     */
    @GetMapping("/presets/activeDiscovery/policy/{policy_id}")
    ActiveDiscoveryPolicy getPolicy(@PathVariable("policy_id") String policyId);

    /**
     * Update an Active Discovery Policy
     *
     * <p>능동 탐색 정책을 수정한다.
     *
     * <p>{@code PUT /presets/activeDiscovery/policy/{policy_id}}
     *
     * <p>{@code 400} - invalid payload
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown policy id
     *
     * @param policyId policy id (uuid) (정책 ID (UUID))
     * @param policy policy payload (정책 페이로드)
     * @return 수정 결과
     */
    @PutMapping("/presets/activeDiscovery/policy/{policy_id}")
    SimpleResponse updatePolicy(
            @PathVariable("policy_id") String policyId,
            @RequestBody ActiveDiscoveryPolicy policy);

    /**
     * Delete an Active Discovery Policy
     *
     * <p>능동 탐색 정책을 삭제한다.
     *
     * <p>{@code DELETE /presets/activeDiscovery/policy/{policy_id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - unknown policy id
     *
     * @param policyId policy id (uuid) (정책 ID (UUID))
     * @return 삭제 결과
     */
    @DeleteMapping("/presets/activeDiscovery/policy/{policy_id}")
    SimpleResponse deletePolicy(@PathVariable("policy_id") String policyId);
}
