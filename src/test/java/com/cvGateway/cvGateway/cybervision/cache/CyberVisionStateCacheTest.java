package com.cvGateway.cvGateway.cybervision.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Version;

class CyberVisionStateCacheTest {

    private final CyberVisionStateCache cache = new CyberVisionStateCache(new FreshnessRegistry());

    @Test
    void 조회한_적_없는_항목은_비어_있다() {
        assertThat(cache.version().get()).isEmpty();
        assertThat(cache.version().freshness().everFetched()).isFalse();
        assertThat(cache.version().freshness().isFailing()).isFalse();
        assertThat(cache.version().freshness().age()).isNull();
    }

    @Test
    void 항목마다_자기_구조체를_따로_들고_있다() {
        Version version = new Version();
        List<DetailedTag> tags = List.of(new DetailedTag());

        cache.version().set(version);
        cache.tags().set(tags);

        // 캐스팅 없이 대입된다는 것 자체가 슬롯의 제네릭이 동작한다는 뜻이다.
        Version storedVersion = cache.version().get().orElseThrow();
        List<DetailedTag> storedTags = cache.tags().get().orElseThrow();

        assertThat(storedVersion).isSameAs(version);
        assertThat(storedTags).isSameAs(tags);
        assertThat(cache.devices().get())
                .as("건드리지 않은 항목은 그대로 비어 있어야 한다")
                .isEmpty();
    }

    @Test
    void 조회에_실패해도_마지막으로_성공한_값은_남는다() {
        Version version = new Version();
        cache.version().set(version);
        Instant succeededAt = cache.version().freshness().fetchedAt();

        cache.version().fail("connection refused");
        Freshness freshness = cache.version().freshness();

        assertThat(cache.version().get()).contains(version);
        assertThat(freshness.isFailing()).isTrue();
        assertThat(freshness.lastError()).isEqualTo("connection refused");
        assertThat(freshness.fetchedAt())
                .as("실패는 성공 시각을 덮어쓰면 안 된다")
                .isEqualTo(succeededAt);
        assertThat(freshness.lastAttemptAt())
                .as("시도 시각은 실패로도 갱신된다")
                .isAfterOrEqualTo(succeededAt);
    }

    @Test
    void 다시_성공하면_실패_기록이_지워진다() {
        cache.version().set(new Version());
        cache.version().fail("timeout");
        cache.version().set(new Version());

        assertThat(cache.version().freshness().isFailing()).isFalse();
        assertThat(cache.version().freshness().lastError()).isNull();
    }

    @Test
    void 한_번도_성공하지_못한_항목은_실패만_기록된다() {
        cache.devices().fail("401 Unauthorized");

        assertThat(cache.devices().get()).isEmpty();
        assertThat(cache.devices().freshness().isFailing()).isTrue();
        assertThat(cache.devices().freshness().everFetched()).isFalse();
        assertThat(cache.devices().freshness().age()).isNull();
    }

    @Test
    void 신선도는_항목과_무관하게_한곳에_모인다() {
        cache.version().set(new Version());
        cache.tags().set(List.of());
        cache.devices().fail("boom");

        assertThat(cache.freshness())
                .as("값 타입이 제각각이어도 신선도는 한 맵에서 훑을 수 있어야 한다")
                .containsOnlyKeys("version", "tags", "devices");
        assertThat(cache.freshness().keySet())
                .as("키 이름 순으로 정렬된다")
                .containsExactly("devices", "tags", "version");
        assertThat(cache.freshness().get("devices").isFailing()).isTrue();
        assertThat(cache.freshness().get("version").isFailing()).isFalse();
    }

    @Test
    void 키로_찾은_슬롯은_항목별_메서드와_같은_슬롯이다() {
        assertThat(cache.slot(StateKeys.VERSION)).isSameAs(cache.version());
        assertThat(cache.slot(StateKeys.SENSORS)).isSameAs(cache.sensors());
    }

    @Test
    void 등록되지_않은_키로는_슬롯을_찾을_수_없다() {
        StateKey<String> unknown = StateKey.of("unknown");

        assertThatThrownBy(() -> cache.slot(unknown))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unknown");
    }

    @Test
    void 값이_없으면_지정한_기본값이_나온다() {
        assertThat(cache.tags().orElse(List.of())).isEmpty();

        cache.tags().set(List.of(new DetailedTag()));

        assertThat(cache.tags().orElse(List.of())).hasSize(1);
    }
}
