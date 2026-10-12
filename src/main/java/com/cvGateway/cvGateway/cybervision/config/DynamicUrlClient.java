package com.cvGateway.cvGateway.cybervision.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import feign.Client;
import feign.Request;
import feign.Response;

/**
 * 요청을 보내기 직전에 Center 주소를 현재 설정된 값으로 갈아끼운다.
 *
 * <p><b>왜 필요한가</b>: {@code @FeignClient(url = "...")} 는 빈을 만들 때 한 번만 평가된다.
 * Center 주소를 DB 에서 받아 런타임에 바꿀 수 있어야 하므로, 고정 주소로는 대응할 수 없다.
 *
 * <p>인터셉터로는 해결되지 않는다. Feign 은 인터셉터를 먼저 실행하고 그 뒤에
 * {@code Target.apply()} 로 주소를 덮어쓰기 때문에, 인터셉터에서 바꾼 주소가 지워진다.
 * 그래서 가장 마지막 단계인 {@link Client} 를 감싸 URL 을 다시 쓴다.
 *
 * <p>경로와 쿼리는 그대로 두고 스킴·호스트·포트만 교체한다. 경로에는 이미
 * {@code /api/3.0} 과 각 오퍼레이션 경로가 들어 있다.
 */
public class DynamicUrlClient implements Client {

    private final Client delegate;
    private final CyberVisionConnectionStore connectionStore;

    public DynamicUrlClient(Client delegate, CyberVisionConnectionStore connectionStore) {
        this.delegate = delegate;
        this.connectionStore = connectionStore;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        String baseUrl = connectionStore.url().orElseThrow(CyberVisionNotConfiguredException::new);
        return delegate.execute(retarget(request, baseUrl), options);
    }

    /** 원래 요청의 경로·쿼리는 유지하고 베이스 주소만 바꾼 새 요청을 만든다. */
    private Request retarget(Request request, String baseUrl) {
        URI original = uriOf(request);

        StringBuilder rewritten = new StringBuilder(baseUrl).append(original.getRawPath());
        if (original.getRawQuery() != null) {
            rewritten.append('?').append(original.getRawQuery());
        }

        return Request.create(
                request.httpMethod(),
                rewritten.toString(),
                request.headers(),
                request.body(),
                request.charset(),
                request.requestTemplate());
    }

    private URI uriOf(Request request) {
        try {
            return new URI(request.url());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Feign 이 만든 URL 을 해석할 수 없습니다: " + request.url(), e);
        }
    }
}
