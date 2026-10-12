package com.cvGateway.cvGateway.cybervision;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import feign.MethodMetadata;

/**
 * 생성된 Feign 클라이언트가 유효한 계약인지 검증한다.
 *
 * <p>{@link SpringMvcContract} 는 Feign 이 프록시를 만들 때 실제로 쓰는 파서다. 여기서 통과하면
 * 경로 변수 누락, 중복 파라미터 이름, 지원하지 않는 애노테이션 조합 같은 오류가 없다는 뜻이다.
 *
 * <p>Spring 컨텍스트를 띄우지 않으므로 DB 나 Cyber Vision 서버 없이 동작한다.
 */
class CyberVisionClientContractTest {

    private static final String API_PACKAGE = "com.cvGateway.cvGateway.cybervision.api";

    /** 스펙(openapi/cyber-vision-3.0.json)의 태그 수와 오퍼레이션 수. */
    private static final int EXPECTED_CLIENTS = 26;
    private static final int EXPECTED_OPERATIONS = 182;

    @Test
    void 생성된_클라이언트가_모두_유효한_Feign_계약이다() {
        List<Class<?>> clients = scanFeignClients();

        assertThat(clients)
                .as("@FeignClient 인터페이스 수")
                .hasSize(EXPECTED_CLIENTS);

        SpringMvcContract contract = new SpringMvcContract();
        int operations = 0;
        for (Class<?> client : clients) {
            List<MethodMetadata> metadata = assertThatParses(contract, client);
            operations += metadata.size();
        }

        assertThat(operations)
                .as("전체 오퍼레이션 수")
                .isEqualTo(EXPECTED_OPERATIONS);
    }

    /** 인터페이스 하나를 파싱하고, 실패하면 어떤 클라이언트인지 드러나도록 감싼다. */
    private List<MethodMetadata> assertThatParses(SpringMvcContract contract, Class<?> client) {
        assertThatCode(() -> contract.parseAndValidateMetadata(client))
                .as("%s 의 Feign 계약", client.getSimpleName())
                .doesNotThrowAnyException();
        return contract.parseAndValidateMetadata(client);
    }

    /**
     * api 패키지에서 {@link FeignClient} 가 붙은 인터페이스를 찾는다.
     *
     * <p>기본 스캐너는 인터페이스를 후보에서 제외하므로 판정을 재정의한다.
     */
    private List<Class<?>> scanFeignClients() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false) {
                    @Override
                    protected boolean isCandidateComponent(
                            org.springframework.beans.factory.annotation.AnnotatedBeanDefinition definition) {
                        return definition.getMetadata().isInterface()
                                && definition.getMetadata().isIndependent();
                    }
                };
        scanner.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));

        return scanner.findCandidateComponents(API_PACKAGE).stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::loadClass)
                .toList();
    }

    private Class<?> loadClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(name + " 를 로드할 수 없습니다", e);
        }
    }
}
