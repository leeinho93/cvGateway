package com.cvGateway.cvGateway.cybervision.model;

import java.util.List;

import lombok.Data;

/**
 * {@code Contents}.
 *
 * <p>플로우 내용 묶음.
 */
@Data
public class Contents {
    /** {@code documentPath} */
    private String documentPath;

    /** {@code name} */
    private String name;

    /** {@code sections} */
    private List<SectionsOfReportType> sections;

    /** {@code tableOfContents} */
    private String tableOfContents;

    /** {@code titlePage} */
    private String titlePage;
}
