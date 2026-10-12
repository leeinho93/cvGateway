package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code Position}.
 *
 * <p>좌표 위치 (그래프 배치 또는 지리 좌표).
 */
@Data
public class Position {
    /** {@code x} */
    private Long x;

    /** {@code y} */
    private Long y;
}
