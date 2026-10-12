package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code IdPayloadForPresetGroup}.
 *
 * <p>프리셋 그룹 필터용 ID 페이로드.
 */
@Data
public class IdPayloadForPresetGroup {
    /** {@code children} */
    private List<PresetGroupFilterPayload> children;

    /** {@code id} */
    private String id;
}
