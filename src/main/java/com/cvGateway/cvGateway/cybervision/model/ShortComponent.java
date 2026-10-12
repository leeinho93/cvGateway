package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code ShortComponent}.
 *
 * <p>컴포넌트 축약 표현.
 */
@Data
public class ShortComponent {
    /** {@code customLabel} */
    private String customLabel;

    /** {@code group} */
    private Group group;

    /** {@code icon} */
    private String icon;

    /** {@code id} */
    private String id;

    /** {@code ip} */
    private String ip;

    /** {@code label} */
    private String label;

    /** {@code mac} */
    private String mac;

    /** {@code originalLabel} */
    private String originalLabel;

    /** {@code tags} */
    private List<Tag> tags;
}
