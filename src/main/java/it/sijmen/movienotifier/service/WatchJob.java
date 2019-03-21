package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.service.pathe.PatheApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WatchJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchJob.class);

    private WatcherRepository watcherRepo;
    private PatheApi api;

    public WatchJob(WatcherRepository repository, PatheApi api) {
        this.watcherRepo = repository;
        this.api = api;
    }

    //todo: this random thing doesnt work
    @Scheduled(cron = "${random.int[0,59]} */12  22-23 * * *")
    @Scheduled(cron = "${random.int[0,59]} */12  0-6 * * *")
    public void executeByNight(){
        LOGGER.info("Executing night job");
        checkCinemasForChangesAndNotifyWatchers();
    }

    @Scheduled(cron = "${random.int[0,59]} */5 6-21 * * *")
    public void executeByDay(){
        LOGGER.info("Executing day job");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkCinemasForChangesAndNotifyWatchers();
    }

    private void checkCinemasForChangesAndNotifyWatchers(){
        long now = System.currentTimeMillis();
        List<Watcher> all = watcherRepo.getAllByBeginIsLessThanAndEndIsGreaterThan(now, now);
        LOGGER.trace("Checking #{} watchers.", all.size());
        api.checkWatcher(all);
    }

}
