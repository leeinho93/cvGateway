package com.cvGateway.cvGateway.cybervision.api;

import java.util.List;

import com.cvGateway.cvGateway.cybervision.model.Pcap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig;

/**
 * Cisco Cyber Vision Classic API - {@code PcapReplays}.
 *
 * <p><b>PCAP 재생 (Pcap Replays)</b>
 * PCAP 파일을 업로드해 저장된 트래픽을 다시 분석시킨다. 업로드는 TUS 프로토콜(POST 로 시작, PATCH 로 청크 전송)을 따른다.
 *
 * <p>오퍼레이션 3개.
 *
 * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라
 * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.
 */
@FeignClient(
        name = "cyberVisionPcapReplays",
        url = "http://cyber-vision.invalid",
        path = "${cyber-vision.base-path:/api/3.0}",
        configuration = CyberVisionFeignConfig.class)
public interface PcapReplaysClient {

    /**
     * List Replayed Pcap Files
     *
     * <p>Replayed pcap list.
     *
     * <p>재생된 PCAP 파일 목록을 조회한다.
     *
     * <p>{@code GET /pcapReplays}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return PCAP 목록
     */
    @GetMapping("/pcapReplays")
    List<Pcap> getPcapReplayList();

    /**
     * Upload a New Pcap File
     *
     * <p>Initiate a TUS upload for a PCAP file and return the upload URL in the Location header. Trailing
     * slash is mandatory.
     *
     * <p>PCAP 파일 업로드를 시작한다.
     *
     * <p>TUS 업로드를 개시하고 Location 헤더로 업로드 URL 을 돌려준다. 경로 끝의 슬래시는 생략할 수 없다.
     *
     * <p>{@code POST /pcapReplays/uploads/}
     *
     * <p>{@code 401} - incorrect token
     *
     * @return 업로드 개시 결과
     */
    @PostMapping("/pcapReplays/uploads/")
    String postPcapReplayUpload();

    /**
     * Upload PCAP File Chunk
     *
     * <p>Resume a TUS PCAP upload by sending the next binary chunk to the upload resource.
     *
     * <p>PCAP 업로드의 다음 청크를 전송한다.
     *
     * <p>TUS 프로토콜 기반 이어올리기. postPcapReplayUpload 로 받은 업로드 리소스에 바이너리 청크를 순차 전송한다.
     *
     * <p>{@code PATCH /pcapReplays/uploads/{tusId}}
     *
     * <p>{@code 204} - Data has been saved.
     * <p>{@code 401} - incorrect token
     * <p>{@code 404} - upload not found
     *
     * @param tusId ID given by the POST endpoint in the Location header (uuid without dashes) (업로드 시작 POST 응답의 Location 헤더로 받은 ID (하이픈 없는 UUID))
     */
    @PatchMapping("/pcapReplays/uploads/{tusId}")
    void patchPcapReplayUpload(@PathVariable("tusId") String tusId);
}
