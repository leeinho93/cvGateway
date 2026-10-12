package com.cvGateway.cvGateway.cybervision.polling;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cvGateway.cvGateway.cybervision.cache.CyberVisionStateCache;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionConnectionStore;
import com.cvGateway.cvGateway.cybervision.config.CyberVisionUnauthorizedException;

import lombok.extern.slf4j.Slf4j;

/**
 * 등급별로 캐시를 갱신하는 스케줄러.
 *
 * <p>스케줄 메서드는 등급 수만큼만 존재한다. 각 메서드는 그 등급에 등록된 항목을 모아 한 번에
 * 조회하므로, 폴링 대상을 늘려도 스케줄러는 그대로다. 항목 추가는 {@link PollingTaskConfig} 에서 한다.
 *
 * <p>{@code cyber-vision.polling.enabled=true} 일 때만 빈이 만들어진다.
 */
@Slf4j
@Component
@EnableScheduling
@EnableConfigurationProperties(PollingProperties.class)
@ConditionalOnProperty(prefix = "cyber-vision.polling", name = "enabled", havingValue = "true")
public class CyberVisionPollingScheduler {

    private final List<PollingTask<?>> tasks;
    private final CyberVisionStateCache cache;
    private final CyberVisionConnectionStore connectionStore;

    /** 접속 정보 없음 로그를 매 회차 남기지 않기 위한 플래그. */
    private final AtomicBoolean warnedAboutMissingConnection = new AtomicBoolean();

    public CyberVisionPollingScheduler(
            List<PollingTask<?>> tasks, CyberVisionStateCache cache, CyberVisionConnectionStore connectionStore) {

        rejectDuplicateKeys(tasks);
        this.tasks = tasks;
        this.cache = cache;
        this.connectionStore = connectionStore;
        log.info("Cyber Vision 폴링 활성화 - 대상 {}건", tasks.size());
    }

    /**
     * 같은 캐시 항목을 두 작업이 채우면 서로의 결과를 덮어쓴다. 조용히 넘어가면 원인을 찾기 어려우니
     * 기동 시점에 실패시킨다.
     */
    private static void rejectDuplicateKeys(List<PollingTask<?>> tasks) {
        Set<String> seen = new HashSet<>();
        List<String> duplicates = tasks.stream()
                .map(task -> task.key().name())
                .filter(name -> !seen.add(name))
                .toList();
        if (!duplicates.isEmpty()) {
            throw new IllegalStateException(
                    "폴링 대상에 중복된 캐시 키가 있습니다: " + duplicates);
        }
    }

    @Scheduled(fixedDelayString = "${cyber-vision.polling.fast}",
            initialDelayString = "${cyber-vision.polling.initial-delay}")
    public void refreshFast() {
        refresh(RefreshTier.FAST);
    }

    @Scheduled(fixedDelayString = "${cyber-vision.polling.normal}",
            initialDelayString = "${cyber-vision.polling.initial-delay}")
    public void refreshNormal() {
        refresh(RefreshTier.NORMAL);
    }

    @Scheduled(fixedDelayString = "${cyber-vision.polling.slow}",
            initialDelayString = "${cyber-vision.polling.initial-delay}")
    public void refreshSlow() {
        refresh(RefreshTier.SLOW);
    }

    @Scheduled(fixedDelayString = "${cyber-vision.polling.rare}",
            initialDelayString = "${cyber-vision.polling.initial-delay}")
    public void refreshRare() {
        refresh(RefreshTier.RARE);
    }

    /**
     * 한 등급에 속한 항목을 모두 갱신한다.
     *
     * <p>항목 하나가 실패해도 나머지는 계속 받아온다. 단 인증 실패는 예외다 — 토큰이 만료되거나
     * 비활성화되면 모든 호출이 똑같이 401 을 내므로, 나머지를 시도해 봐야 Center 만 두드릴 뿐이다.
     * 그래서 401 을 만나면 이번 회차를 즉시 중단한다.
     */
    private void refresh(RefreshTier tier) {
        // 접속 정보가 아직 주입되지 않았으면 호출해 봐야 전부 실패한다. 조용히 건너뛰고,
        // 로그는 처음 한 번만 남긴다. 매 회차 같은 경고를 쌓아 봐야 도움이 되지 않는다.
        if (!connectionStore.isConfigured()) {
            if (warnedAboutMissingConnection.compareAndSet(false, true)) {
                log.warn("Cyber Vision 접속 정보가 없어 폴링을 건너뛴다."
                        + " POST /api/cyber-vision/connection 으로 주입되면 자동으로 재개된다.");
            }
            return;
        }
        warnedAboutMissingConnection.set(false);

        long startedAt = System.currentTimeMillis();
        int succeeded = 0;
        int failed = 0;

        for (PollingTask<?> task : tasks) {
            if (task.tier() != tier) {
                continue;
            }
            try {
                task.runInto(cache);
                succeeded++;
            } catch (CyberVisionUnauthorizedException e) {
                task.recordFailure(cache, e.getMessage());
                log.error("Cyber Vision 인증 실패 - {} 등급 갱신을 중단한다."
                        + " 토큰이 만료됐거나 비활성화됐는지 확인이 필요하다. (항목: {})",
                        tier, task.key(), e);
                return;
            } catch (RuntimeException e) {
                task.recordFailure(cache, e.getMessage());
                failed++;
                log.warn("Cyber Vision 조회 실패 - 항목 {}: {}", task.key(), e.getMessage());
            }
        }

        long elapsed = System.currentTimeMillis() - startedAt;
        if (failed > 0) {
            log.warn("{} 등급 갱신 완료 - 성공 {}건, 실패 {}건, {}ms", tier, succeeded, failed, elapsed);
        } else if (succeeded > 0) {
            log.debug("{} 등급 갱신 완료 - {}건, {}ms", tier, succeeded, elapsed);
        }
    }
}
