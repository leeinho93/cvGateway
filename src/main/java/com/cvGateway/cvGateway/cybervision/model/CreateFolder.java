package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code CreateFolder}.
 *
 * <p>센서 탐색기 폴더 생성 요청 본문.
 */
@Data
public class CreateFolder {
    /** {@code description} */
    private String description;

    /** {@code label} */
    private String label;

    /** {@code location} */
    private String location;

    /** {@code properties} */
    private List<String> properties;

    /** {@code selection} */
    private List<SensorExplorerSelection> selection;
}
