package com.cvGateway.cvGateway.cybervision.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import feign.Client;
import feign.Request;
import feign.Response;

/**
 * 요청 직전 Center 주소 치환을 검증한다.
 *
 * <p>{@code @FeignClient(url = ...)} 는 기동 시 한 번만 평가되므로, DB 에서 오는 런타임 주소를
 * 반영하려면 이 치환이 반드시 동작해야 한다. 여기가 깨지면 모든 호출이 자리표시자 주소로 나간다.
 */
class DynamicUrlClientTest {

    private final CyberVisionConnectionStore store = new CyberVisionConnectionStore();
    private final RecordingClient delegate = new RecordingClient();
    private final DynamicUrlClient client = new DynamicUrlClient(delegate, store);

    private void configure(String centerUrl) {
        store.update(CyberVisionConnection.of(
                "1", centerUrl, "sw-01", "5.5.0", "ics-token", Instant.now()));
    }

    private static Request request(String url) {
        return Request.create(
                Request.HttpMethod.GET, url, Map.of(), Request.Body.empty(), null);
    }

    @Test
    void 자리표시자_주소를_설정된_주소로_바꾼다() throws IOException {
        configure("https://192.168.0.10");

        client.execute(request("http://cyber-vision.invalid/api/3.0/sensors"), options());

        assertThat(delegate.lastUrl).isEqualTo("https://192.168.0.10/api/3.0/sensors");
    }

    @Test
    void 경로와_쿼리는_그대로_유지한다() throws IOException {
        configure("https://192.168.0.10:8443");

        client.execute(
                request("http://cyber-vision.invalid/api/3.0/devices?page=2&size=50"), options());

        assertThat(delegate.lastUrl)
                .as("포트가 있는 주소도 그대로 쓰이고 쿼리가 유실되지 않아야 한다")
                .isEqualTo("https://192.168.0.10:8443/api/3.0/devices?page=2&size=50");
    }

    @Test
    void 주소_교체가_다음_호출에_즉시_반영된다() throws IOException {
        configure("https://old-center");
        client.execute(request("http://cyber-vision.invalid/api/3.0/version"), options());
        assertThat(delegate.lastUrl).startsWith("https://old-center");

        configure("https://new-center");
        client.execute(request("http://cyber-vision.invalid/api/3.0/version"), options());

        assertThat(delegate.lastUrl)
                .as("Center 를 바꾸면 재기동 없이 다음 호출부터 새 주소로 가야 한다")
                .startsWith("https://new-center");
    }

    @Test
    void 뒤_슬래시가_붙은_주소로도_이중_슬래시가_생기지_않는다() throws IOException {
        configure("https://192.168.0.10/");

        client.execute(request("http://cyber-vision.invalid/api/3.0/tags"), options());

        assertThat(delegate.lastUrl).isEqualTo("https://192.168.0.10/api/3.0/tags");
    }

    @Test
    void 접속_정보가_없으면_호출하지_않고_실패한다() {
        assertThatThrownBy(() -> client.execute(
                request("http://cyber-vision.invalid/api/3.0/sensors"), options()))
                .isInstanceOf(CyberVisionNotConfiguredException.class);

        assertThat(delegate.lastUrl)
                .as("보낼 주소가 없으면 위임 클라이언트까지 가지 않아야 한다")
                .isNull();
    }

    private static Request.Options options() {
        return new Request.Options();
    }

    /** 최종적으로 어떤 URL 로 나갔는지만 기록한다. */
    private static class RecordingClient implements Client {

        private String lastUrl;

        @Override
        public Response execute(Request request, Request.Options options) {
            lastUrl = request.url();
            return Response.builder()
                    .status(200)
                    .reason("OK")
                    .request(request)
                    .headers(Map.of("Content-Type", List.of("application/json")))
                    .body(new byte[0])
                    .build();
        }
    }
}
