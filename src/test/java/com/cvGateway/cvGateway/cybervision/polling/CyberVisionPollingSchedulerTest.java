package com.cvGateway.cvGateway.cybervision.polling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cvGateway.cvGateway.cybervision.cache.CyberVisionStateCache;
import com.cvGateway.cvGateway.cybervision.cache.FreshnessRegistry;
import com.cvGateway.cvGateway.cybervision.cache.StateKeys;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnection;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionUnauthorizedException;
import com.cvGateway.cvGateway.cybervision.model.DetailedTag;
import com.cvGateway.cvGateway.cybervision.model.Device;
import com.cvGateway.cvGateway.cybervision.model.Version;

/**
 * 스케줄러의 등급 분리와 실패 처리 동작을 검증한다.
 *
 * <p>{@link PollingTask} 는 조회 방법을 {@code Supplier} 로 받으므로 Feign 클라이언트 없이
 * 람다만으로 시나리오를 구성할 수 있다. Cyber Vision 서버가 필요 없다.
 */
class CyberVisionPollingSchedulerTest {

    private final CyberVisionStateCache cache = new CyberVisionStateCache(new FreshnessRegistry());
    private final CyberVisionConnectionStore connectionStore = new CyberVisionConnectionStore();

    @BeforeEach
    void injectConnection() {
        // 접속 정보가 없으면 스케줄러가 회차를 통째로 건너뛴다.
        // 대부분의 검증은 접속 정보가 있는 상태를 전제한다.
        connectionStore.update(testConnection());
    }

    private static CyberVisionConnection testConnection() {
        return CyberVisionConnection.of(
                "1", "https://center.test", "sw-01", "5.5.0", "ics-test-token", java.time.Instant.now());
    }

    @Test
    void 접속_정보가_없으면_아무것도_호출하지_않는다() {
        connectionStore.clear();
        AtomicInteger calls = new AtomicInteger();
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, () -> {
                    calls.incrementAndGet();
                    return new Version();
                }));

        scheduler(tasks).refreshFast();

        assertThat(calls)
                .as("접속 정보 없이 호출하면 전부 실패할 뿐이므로 아예 나가지 않아야 한다")
                .hasValue(0);
        assertThat(cache.version().get()).isEmpty();
    }

    @Test
    void 접속_정보가_주입되면_다음_회차부터_다시_받아온다() {
        connectionStore.clear();
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, Version::new));
        CyberVisionPollingScheduler scheduler = scheduler(tasks);

        scheduler.refreshFast();
        assertThat(cache.version().get()).isEmpty();

        connectionStore.update(testConnection());
        scheduler.refreshFast();

        assertThat(cache.version().get())
                .as("접속 정보가 들어오면 재기동 없이 폴링이 재개돼야 한다")
                .isPresent();
    }

    @Test
    void 해당_등급의_항목만_갱신한다() {
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, Version::new),
                new PollingTask<>(StateKeys.DEVICES, RefreshTier.SLOW, Device::new));

        scheduler(tasks).refreshFast();

        assertThat(cache.version().get()).isPresent();
        assertThat(cache.devices().get())
                .as("다른 등급은 건드리지 않아야 한다")
                .isEmpty();
    }

    @Test
    void 항목_하나가_실패해도_나머지는_계속_받아온다() {
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, () -> {
                    throw new IllegalStateException("boom");
                }),
                new PollingTask<>(StateKeys.TAGS, RefreshTier.FAST, () -> List.of(new DetailedTag())));

        scheduler(tasks).refreshFast();

        assertThat(cache.version().get()).isEmpty();
        assertThat(cache.version().freshness().lastError()).isEqualTo("boom");
        assertThat(cache.tags().get())
                .as("앞 항목의 실패가 뒤 항목을 막으면 안 된다")
                .isPresent();
    }

    @Test
    void 인증_실패를_만나면_그_회차를_즉시_중단한다() {
        AtomicInteger callsAfterFailure = new AtomicInteger();
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, Version::new),
                new PollingTask<>(StateKeys.TAGS, RefreshTier.FAST, () -> {
                    throw new CyberVisionUnauthorizedException("TagsClient#tagList()", 401, "incorrect token");
                }),
                new PollingTask<>(StateKeys.SENSORS, RefreshTier.FAST, () -> {
                    callsAfterFailure.incrementAndGet();
                    return List.of();
                }));

        scheduler(tasks).refreshFast();

        assertThat(cache.version().get()).as("401 이전 항목은 갱신된다").isPresent();
        assertThat(cache.tags().freshness().isFailing()).isTrue();
        assertThat(callsAfterFailure)
                .as("토큰이 거부된 뒤에는 남은 호출을 시도하지 않아야 한다")
                .hasValue(0);
        assertThat(cache.sensors().get()).isEmpty();
    }

    @Test
    void 등급별_스케줄_메서드가_각_등급에_대응한다() {
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.NORMAL, Version::new),
                new PollingTask<>(StateKeys.DEVICES, RefreshTier.RARE, Device::new));

        CyberVisionPollingScheduler scheduler = scheduler(tasks);
        scheduler.refreshNormal();
        scheduler.refreshRare();

        assertThat(cache.version().get()).isPresent();
        assertThat(cache.devices().get()).isPresent();
    }

    @Test
    void 같은_항목을_두_번_등록하면_기동에_실패한다() {
        List<PollingTask<?>> tasks = List.of(
                new PollingTask<>(StateKeys.VERSION, RefreshTier.FAST, Version::new),
                new PollingTask<>(StateKeys.VERSION, RefreshTier.SLOW, Version::new));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> scheduler(tasks))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("version");
    }

    private CyberVisionPollingScheduler scheduler(List<PollingTask<?>> tasks) {
        return new CyberVisionPollingScheduler(tasks, cache, connectionStore);
    }
}
