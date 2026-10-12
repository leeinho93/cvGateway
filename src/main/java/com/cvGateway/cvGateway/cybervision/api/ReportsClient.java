package com.cvGateway.cvGateway.cybervision.api;

import com.cvGateway.cvGateway.cybervision.model.CreateReportsMetadataNBReq;
import com.cvGateway.cvGateway.cybervision.model.PostReportsLogoResp;
import com.cvGateway.cvGateway.cybervision.model.ReportMetadata;
import com.cvGateway.cvGateway.cybervision.model.Reports;
import com.cvGateway.cvGateway.cybervision.model.ReportsMetadataAndReportsResp;
import com.cvGateway.cvGateway.cybervision.model.ReportsType;
import com.cvGateway.cvGateway.cybervision.model.UpdateReportsMetadataPayLoad;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code Reports}.
 *
 * <p><b>리포트 (Reports)</b>
 * 리포트 메타데이터를 정의하고 이를 근거로 리포트를 생성·다운로드한다.
 *
 * <p>오퍼레이션 10개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionReports",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface ReportsClient {

    /**
     * List All Existing Reports Metadata
     *
     * <p>리포트 메타데이터 전체를 조회한다.
     *
     * <p>{@code GET /reports2/reports-metadata}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param page page number (페이지 번호)
     * @param size records per page (페이지당 레코드 수)
     * @param filter filtering criteria, Eg: field:criteria (필터 조건. 예: field:criteria)
     * @param sort fields to sort, Eg: field:desc (정렬할 필드. 예: field:desc)
     * @return 메타데이터 목록
     */
    @GetMapping("/reports2/reports-metadata")
    ReportMetadata listReportsMetadata(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "sort", required = false) Integer sort);

    /**
     * Create Reports Metadata
     *
     * <p>리포트 메타데이터를 생성한다.
     *
     * <p>{@code POST /reports2/reports-metadata}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param reportsMetadata reports metadata payload (리포트 메타데이터 페이로드)
     * @return 생성 결과
     */
    @PostMapping("/reports2/reports-metadata")
    ReportsMetadataAndReportsResp createReportsMetadata(@RequestBody CreateReportsMetadataNBReq reportsMetadata);

    /**
     * Upload Logo and Return Logo Id
     *
     * <p>Uploads logo and returns logo Id.
     *
     * <p>로고 파일을 업로드하고 로고 ID 를 반환한다.
     *
     * <p>{@code POST /reports2/reports-metadata/logo}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param file provided logo file will be uploaded into system (업로드할 로고 파일)
     * @return 로고 ID
     */
    @PostMapping(value = "/reports2/reports-metadata/logo", consumes = "multipart/form-data")
    PostReportsLogoResp uploadLogo(@RequestPart("file") MultipartFile file);

    /**
     * Download Report by ID and File Type
     *
     * <p>Download report by reportId and filetype.
     *
     * <p>리포트를 지정한 파일 형식으로 내려받는다.
     *
     * <p>응답은 바이너리다. 반환 타입이 byte[] 이므로 대용량 리포트는 메모리 사용량에 유의한다.
     *
     * <p>{@code GET /reports2/reports-metadata/reports/{reports-id}/download}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param reportsId Id of reports (리포트 ID)
     * @param filetype File type of reports that is being downloaded Eg: download?filetype=PDF (다운로드할 리포트 파일 형식. 예: download?filetype=PDF)
     * @return 리포트 파일 바이너리
     */
    @GetMapping("/reports2/reports-metadata/reports/{reports-id}/download")
    byte[] downloadReports(
            @PathVariable("reports-id") String reportsId,
            @RequestParam(name = "filetype", required = true) String filetype);

    /**
     * Update Reports Metadata
     *
     * <p>리포트 메타데이터를 수정한다.
     *
     * <p>{@code PUT /reports2/reports-metadata/{id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param id Id of reports metadata (리포트 메타데이터 ID)
     * @param reportsMetadata reports metadata payload (리포트 메타데이터 페이로드)
     * @return 수정 결과
     */
    @PutMapping("/reports2/reports-metadata/{id}")
    ReportsMetadataAndReportsResp updateReportsMetadata(
            @PathVariable("id") String id,
            @RequestBody UpdateReportsMetadataPayLoad reportsMetadata);

    /**
     * Delete Reports Metadata by Id
     *
     * <p>리포트 메타데이터를 삭제한다.
     *
     * <p>{@code DELETE /reports2/reports-metadata/{id}}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param id Id of reports metadata (리포트 메타데이터 ID)
     * @return 삭제 결과
     */
    @DeleteMapping("/reports2/reports-metadata/{id}")
    String deleteReportsMetadata(@PathVariable("id") String id);

    /**
     * List Reports by Metadata Id
     *
     * <p>메타데이터 ID 기준으로 생성된 리포트 목록을 조회한다.
     *
     * <p>{@code GET /reports2/reports-metadata/{id}/reports}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param id Id of reports metadata (리포트 메타데이터 ID)
     * @return 리포트 목록
     */
    @GetMapping("/reports2/reports-metadata/{id}/reports")
    Reports listReports(@PathVariable("id") String id);

    /**
     * Generate Reports by Metadata ID
     *
     * <p>Generate reports for given reports metadata Id.
     *
     * <p>리포트 메타데이터 ID 기준으로 리포트를 생성한다.
     *
     * <p>{@code POST /reports2/reports-metadata/{id}/reports}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param id Id of reports metadata (리포트 메타데이터 ID)
     * @return 생성 결과
     */
    @PostMapping("/reports2/reports-metadata/{id}/reports")
    ReportsMetadataAndReportsResp runReports(@PathVariable("id") String id);

    /**
     * Delete Report by Report ID
     *
     * <p>Delete reports that matches to reportId.
     *
     * <p>리포트 ID 로 리포트를 삭제한다.
     *
     * <p>{@code DELETE /reports2/reports-metadata/{id}/reports/{reportsId}}
     *
     * <p>{@code 204} - success response
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @param id Id of reports metadata (리포트 메타데이터 ID)
     * @param reportsId Id of reports (리포트 ID)
     */
    @DeleteMapping("/reports2/reports-metadata/{id}/reports/{reportsId}")
    void deleteReports(
            @PathVariable("id") String id,
            @PathVariable("reportsId") String reportsId);

    /**
     * List Available Report Types
     *
     * <p>List all available report templates and their metadata (id, label, layout, output formats,
     * sections).
     *
     * <p>사용 가능한 리포트 유형을 조회한다.
     *
     * <p>리포트 템플릿과 그 메타데이터(id, label, layout, 출력 형식, 섹션)를 반환한다.
     *
     * <p>{@code GET /reports2/reports-type}
     *
     * <p>{@code 400} - invalid parameter
     * <p>{@code 401} - incorrect token
     *
     * @return 리포트 유형 목록
     */
    @GetMapping("/reports2/reports-type")
    ReportsType listReportsTypes();
}
