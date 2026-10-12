package com.cvGateway.cvGateway.cybervision.polling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class PresetFanOutTest {

    @Test
    void 프리셋마다_한_번씩_호출해_ID_를_키로_모은다() {
        Map<String, String> result = PresetFanOut
                .byPreset(List.of("p1", "p2"), presetId -> "value-" + presetId)
                .get();

        assertThat(result).containsExactly(
                Map.entry("p1", "value-p1"),
                Map.entry("p2", "value-p2"));
    }

    @Test
    void 프리셋_하나가_실패해도_나머지_결과는_살린다() {
        Map<String, String> result = PresetFanOut
                .byPreset(List.of("p1", "broken", "p3"), presetId -> {
                    if ("broken".equals(presetId)) {
                        throw new IllegalStateException("404 unknown preset");
                    }
                    return "value-" + presetId;
                })
                .get();

        assertThat(result)
                .as("삭제된 프리셋 하나 때문에 항목 전체가 비면 안 된다")
                .containsOnlyKeys("p1", "p3");
    }

    @Test
    void 전부_실패하면_예외를_던져_폴링_실패로_남긴다() {
        assertThatThrownBy(() -> PresetFanOut
                .byPreset(List.of("p1", "p2"), presetId -> {
                    throw new IllegalStateException("connection refused");
                })
                .get())
                .as("전부 실패는 개별 프리셋 문제가 아니라 연결·인증 문제다")
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("connection refused");
    }

    @Test
    void 지정한_프리셋이_없으면_빈_맵이고_실패가_아니다() {
        Map<String, String> result = PresetFanOut
                .byPreset(List.of(), presetId -> "never called")
                .get();

        assertThat(result).isEmpty();
    }

    @Test
    void 프리셋과_유형의_모든_조합을_2단_맵으로_모은다() {
        Map<String, Map<String, String>> result = PresetFanOut
                .byPresetAndType(List.of("p1", "p2"), List.of("dns", "http"),
                        (presetId, type) -> presetId + "/" + type)
                .get();

        assertThat(result).containsOnlyKeys("p1", "p2");
        assertThat(result.get("p1")).containsExactly(
                Map.entry("dns", "p1/dns"),
                Map.entry("http", "p1/http"));
    }

    @Test
    void 조합_일부가_실패하면_성공한_조합만_남는다() {
        Map<String, Map<String, String>> result = PresetFanOut
                .byPresetAndType(List.of("p1"), List.of("dns", "smb"), (presetId, type) -> {
                    if ("smb".equals(type)) {
                        throw new IllegalStateException("not supported");
                    }
                    return presetId + "/" + type;
                })
                .get();

        assertThat(result.get("p1"))
                .as("유형 하나가 안 되더라도 나머지 유형은 담겨야 한다")
                .containsOnlyKeys("dns");
    }

    @Test
    void 한_프리셋의_모든_유형이_실패하면_그_프리셋은_아예_빠진다() {
        Map<String, Map<String, String>> result = PresetFanOut
                .byPresetAndType(List.of("p1", "broken"), List.of("dns"), (presetId, type) -> {
                    if ("broken".equals(presetId)) {
                        throw new IllegalStateException("404");
                    }
                    return presetId + "/" + type;
                })
                .get();

        assertThat(result)
                .as("빈 맵이 딸린 프리셋 키를 남기면 소비하는 쪽이 헷갈린다")
                .containsOnlyKeys("p1");
    }
}
