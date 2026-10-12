package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code LightFolder}.
 *
 * <p>폴더 경량 표현 (id, label).
 */
@Data
public class LightFolder {
    /** {@code id} */
    private String id;

    /** {@code label} */
    private String label;
}
