package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Activity;
import com.cvGateway.cvGateway.cybervision.model.BaselineDifference;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.DifferenceReview;
import com.cvGateway.cvGateway.cybervision.model.Flow;
import com.cvGateway.cvGateway.cybervision.model.Property;
import com.cvGateway.cvGateway.cybervision.model.VariableMonitor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code BaselineDifferences}.
 *
 * <p><b>베이스라인 차이 (Baseline Differences)</b>
 * 현재 네트워크 상태와 기준선(베이스라인)의 차이를 조회하고, 차이를 승인/거부 처리한다.
 *
 * <p>오퍼레이션 10개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionBaselineDifferences",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface BaselineDifferencesClient {

    /**
     * Get Activity Variables Causing Baseline Differences
     *
     * <p>베이스라인 차이를 유발한 액티비티 변수 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{baseline_id}/activities/{activity_id}/visualisations/variable-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline or activity not found
     *
     * @param baselineId baseline id (uuid) (베이스라인 ID (UUID))
     * @param activityId activity id (uuid) (액티비티 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 변수 목록
     */
    @GetMapping("/baselines/{baseline_id}/activities/{activity_id}/visualisations/variable-list")
    List<VariableMonitor> getBaselinesDifferencesVariableList(
            @PathVariable("baseline_id") String baselineId,
            @PathVariable("activity_id") String activityId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Component Properties Causing Baseline Differences
     *
     * <p>베이스라인 차이를 유발한 컴포넌트 속성 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{baseline_id}/components/{component_id}/visualisations/property-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline not found
     *
     * @param baselineId baseline id (uuid) (베이스라인 ID (UUID))
     * @param componentId component id (uuid) (컴포넌트 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 속성 목록
     */
    @GetMapping("/baselines/{baseline_id}/components/{component_id}/visualisations/property-list")
    List<Property> getBaselinesDifferencesPropertyList(
            @PathVariable("baseline_id") String baselineId,
            @PathVariable("component_id") String componentId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Get Baseline Differences by Target
     *
     * <p>대상별로 베이스라인 차이를 조회한다.
     *
     * <p>{@code GET /baselines/{baseline_id}/differences/{target}/{id}}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline or target differences not found
     *
     * @param baselineId baseline id (uuid) (베이스라인 ID (UUID))
     * @param target the target on which to get the differences (차이를 조회할 대상)
     * @param id target id (uuid) (대상 ID (UUID))
     * @return 차이 목록
     */
    @GetMapping("/baselines/{baseline_id}/differences/{target}/{id}")
    Activity getBaselineDifferencesOnTarget(
            @PathVariable("baseline_id") String baselineId,
            @PathVariable("target") String target,
            @PathVariable("id") String id);

    /**
     * Get Tags Causing Baseline Difference
     *
     * <p>베이스라인 차이를 유발한 태그 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{baseline_id}/{target}/{target_id}/visualisations/tag-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline id or target not found
     *
     * @param baselineId baseline id (uuid) (베이스라인 ID (UUID))
     * @param target the target on which to get the differences (차이를 조회할 대상)
     * @param targetId target id (uuid) (대상 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @return 태그 목록
     */
    @GetMapping("/baselines/{baseline_id}/{target}/{target_id}/visualisations/tag-list")
    List<DetailedTag> getBaselinesDifferencesTagList(
            @PathVariable("baseline_id") String baselineId,
            @PathVariable("target") String target,
            @PathVariable("target_id") String targetId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter);

    /**
     * Review Baseline Differences by Target
     *
     * <p>대상 단위로 베이스라인 차이를 일괄 검토 처리한다.
     *
     * <p>{@code POST /baselines/{baseline_id}/{target}/{target_id}/{diff_type}/{action}}
     *
     * <p>{@code 204} - difference review success
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - target or baseline not found
     *
     * @param baselineId baseline id (uuid) (베이스라인 ID (UUID))
     * @param target the target on which to get the differences (차이를 조회할 대상)
     * @param targetId target id (uuid) (대상 ID (UUID))
     * @param diffType type of difference (차이 유형)
     * @param action the action to do on the differences (차이 목록에 적용할 처리)
     * @param differenceReview difference review payload (차이 검토 페이로드)
     */
    @PostMapping("/baselines/{baseline_id}/{target}/{target_id}/{diff_type}/{action}")
    void reviewAllBaselineDifferences(
            @PathVariable("baseline_id") String baselineId,
            @PathVariable("target") String target,
            @PathVariable("target_id") String targetId,
            @PathVariable("diff_type") String diffType,
            @PathVariable("action") String action,
            @RequestBody DifferenceReview differenceReview);

    /**
     * Get Differences for a Baseline
     *
     * <p>베이스라인의 차이 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{id}/differences}
     *
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline not found
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @return 차이 목록
     */
    @GetMapping("/baselines/{id}/differences")
    List<BaselineDifference> getBaselineDifferences(@PathVariable("id") String id);

    /**
     * Review Differences for a Baseline
     *
     * <p>베이스라인 하나의 차이 전체를 검토 처리한다.
     *
     * <p>{@code POST /baselines/{id}/differences/{action}}
     *
     * <p>{@code 204} - difference review success
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline not found
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @param action the action to do on the differences (차이 목록에 적용할 처리)
     * @param differenceReview difference review payload (차이 검토 페이로드)
     */
    @PostMapping("/baselines/{id}/differences/{action}")
    void reviewBaselineDifferences(
            @PathVariable("id") String id,
            @PathVariable("action") String action,
            @RequestBody DifferenceReview differenceReview);

    /**
     * Get Flows Causing Baseline Differences
     *
     * <p>베이스라인 차이를 유발한 플로우 목록을 조회한다.
     *
     * <p>{@code GET /baselines/{id}/{target}/{targetId}/visualisations/flow-list}
     *
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - baseline or target not found
     *
     * @param id baseline id (uuid) (베이스라인 ID (UUID))
     * @param target the target on which to get the differences (차이를 조회할 대상)
     * @param targetId target id (uuid) (대상 ID (UUID))
     * @param page pagination - the page number (페이지 번호 (페이지네이션))
     * @param size pagination - the number of items per page (페이지당 항목 수 (페이지네이션))
     * @param sort the fields to sort. Example: field1,field2:DESC (정렬할 필드. 예: field1,field2:DESC)
     * @param filter the filter. Example: field1:val,field2:abc (필터 조건. 예: field1:val,field2:abc)
     * @param prop if prop = 'tags', returned data is a list of DetailedTag (prop 이 'tags' 이면 DetailedTag 목록이 반환된다)
     * @return 플로우 목록
     */
    @GetMapping("/baselines/{id}/{target}/{targetId}/visualisations/flow-list")
    List<Flow> getBaselinesDifferencesFlowList(
            @PathVariable("id") String id,
            @PathVariable("target") String target,
            @PathVariable("targetId") String targetId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "prop", required = false) String prop);

    /**
     * Review a Baseline Difference
     *
     * <p>개별 베이스라인 차이를 검토 처리한다.
     *
     * <p>{@code POST /differences/{id}/{action}}
     *
     * <p>{@code 204} - difference review success
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - difference not found
     *
     * @param id difference id (uuid) (차이 ID (UUID))
     * @param action the action to do on the difference (해당 차이에 적용할 처리)
     * @param differenceReview difference review payload (차이 검토 페이로드)
     */
    @PostMapping("/differences/{id}/{action}")
    void reviewBaselineDifference(
            @PathVariable("id") String id,
            @PathVariable("action") String action,
            @RequestBody DifferenceReview differenceReview);

    /**
     * Review Differences for All Baselines
     *
     * <p>모든 베이스라인의 차이를 검토 처리한다.
     *
     * <p>{@code POST /presets/baselines/differences/{action}}
     *
     * <p>{@code 204} - difference review success
     * <p>{@code 400} - bad request
     * <p>{@code 401} - incorrect token
     * <p>{@code 402} - accessible only in advantage mode
     * <p>{@code 404} - no baseline found
     *
     * @param action the action to do on the differences (차이 목록에 적용할 처리)
     * @param differenceReview difference review payload (차이 검토 페이로드)
     */
    @PostMapping("/presets/baselines/differences/{action}")
    void reviewBaselineListDifferences(
            @PathVariable("action") String action,
            @RequestBody DifferenceReview differenceReview);
}
