package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.service.pathe.PatheNotifier;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WatchJob {

  private static final Logger LOGGER = LoggerFactory.getLogger(WatchJob.class);

  private final boolean disabled;
  private final WatcherRepository watcherRepo;
  private final PatheNotifier api;

  public WatchJob(
      @Value("${disable.checker}") boolean disabled,
      WatcherRepository repository,
      PatheNotifier api) {
    this.disabled = disabled;
    this.watcherRepo = repository;
    this.api = api;
    if (disabled) LOGGER.info("Checking is disabled. No notifications will be sent.");
  }

  @Scheduled(cron = "0 */12  0-6 * * *")
  @Scheduled(cron = "0 */5   6-21 * * *")
  @Scheduled(cron = "0 */12  22-23 * * *")
  public void executeByNight() {
    if (disabled) return;
    delaySome();
    LOGGER.info("Executing job");
    checkCinemasForChangesAndNotifyWatchers();
  }

  private void delaySome() {
    // sleep between 0 and 59.999 seconds to spread our checking
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(0, 60 * 1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void checkCinemasForChangesAndNotifyWatchers() {
    long now = System.currentTimeMillis();
    List<Watcher> all = watcherRepo.getAllByBeginIsLessThanAndEndIsGreaterThan(now, now);
    LOGGER.trace("Checking #{} watchers.", all.size());
    api.checkWatcher(all);
  }
}
