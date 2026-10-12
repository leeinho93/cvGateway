package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code LightDevice}.
 *
 * <p>디바이스 경량 표현. 목록 조회용.
 */
@Data
public class LightDevice {
    /** {@code customLabel} */
    private String customLabel;

    /** {@code deviceType} */
    private String deviceType;

    /** {@code group} */
    private Group group;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private List<String> ip;

    /** {@code isDevice} */
    private Boolean isDevice;

    /** {@code label} */
    private String label;

    /** {@code mac} */
    private List<String> mac;

    /** {@code originalLabel} */
    private String originalLabel;

    /** {@code tags} */
    private List<Tag> tags;
}
