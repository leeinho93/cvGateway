package com.cvGateway.cvGateway.cybervision.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

/**
 * Cyber Vision 의 에러 응답을 {@link CyberVisionApiException} 으로 변환한다.
 *
 * <p>기본 {@code ErrorDecoder} 는 상태 코드만 담은 {@code FeignException} 을 던져 원인 파악이 어렵다.
 * 여기서는 응답 본문까지 함께 실어 보낸다.
 */
public class CyberVisionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = readBody(response);
        if (response.status() == 401) {
            return new CyberVisionUnauthorizedException(methodKey, response.status(), body);
        }
        return new CyberVisionApiException(methodKey, response.status(), body);
    }

    /** 응답 본문을 문자열로 읽는다. 본문이 없거나 읽기에 실패하면 빈 문자열. */
    private String readBody(Response response) {
        if (response.body() == null) {
            return "";
        }
        try {
            return Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        } catch (IOException e) {
            return "";
        }
    }
}
