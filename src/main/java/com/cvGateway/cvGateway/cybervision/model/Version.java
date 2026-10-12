package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Version}.
 *
 * <p>Center 버전 정보.
 */
@Data
public class Version {
    /** {@code build} */
    private Long build;

    /** {@code incr} */
    private Long incr;

    /** {@code major} */
    private Long major;

    /** {@code minor} */
    private Long minor;
}
