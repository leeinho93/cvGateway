package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Pcap}.
 *
 * <p>재생된 PCAP 파일 정보.
 */
@Data
public class Pcap {
    /** {@code dpiStatus} */
    private String dpiStatus;

    /** {@code id} */
    private String id;

    /** {@code name} */
    private String name;

    /** {@code nidsStatus} */
    private String nidsStatus;

    /** {@code presetId} */
    private String presetId;

    /** {@code size} */
    private Long size;

    /** {@code startTime} */
    private Long startTime;

    /** {@code tusUploadId} */
    private String tusUploadId;

    /** {@code uploadProgress} */
    private Long uploadProgress;

    /** {@code uploadedTime} */
    private Long uploadedTime;
}
