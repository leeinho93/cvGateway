package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code UpdateFolder}.
 *
 * <p>폴더 수정 요청 본문.
 */
@Data
public class UpdateFolder {
    /** {@code description} */
    private String description;

    /** {@code label} */
    private String label;

    /** {@code location} */
    private String location;
}
