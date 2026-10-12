/**
 * Cisco Cyber Vision Classic API 를 호출하는 Feign 클라이언트 26개 (오퍼레이션 182개).
 *
 * <p>스펙의 태그 하나가 클라이언트 인터페이스 하나에 대응한다.
 *
 * <ul>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ActiveDiscoveryClient} - 14개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ActivitiesClient} - 6개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.BaselineDifferencesClient} - 10개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.BaselinesClient} - 8개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ComponentsClient} - 11개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.CustomNetworkClient} - 5개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.CustomPropertiesClient} - 5개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.DashboardClient} - 18개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.DeploymentsClient} - 6개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.DevicesClient} - 11개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ExternalCommunicationClient} - 1개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.FlowsClient} - 6개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.GlobalCredentialsClient} - 4개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.GraphClient} - 2개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.GroupsClient} - 6개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.MiscClient} - 1개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ParametersClient} - 5개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.PcapReplaysClient} - 3개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.PresetsClient} - 13개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.ReportsClient} - 10개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.RiskScoresClient} - 2개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.SensorExplorerClient} - 9개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.SensorTemplatesClient} - 2개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.SensorsClient} - 12개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.TagsClient} - 3개</li>
 * <li>{@link com.cvGateway.cvGateway.cybervision.api.VulnerabilitiesClient} - 9개</li>
 * </ul>
 *
 * <p>{@code tools/gen.py} 가 생성한다. <b>직접 수정하지 말 것</b> — 재생성 시 사라진다.
 *
 * <p>인증 토큰({@code x-token-id})과 {@code Accept-Language} 헤더는 각 메서드가 아니라
 * {@link com.cvGateway.cvGateway.cybervision.config.CyberVisionFeignConfig} 의 인터셉터가 일괄로 붙인다.
 */
package com.cvGateway.cvGateway.cybervision.api;
