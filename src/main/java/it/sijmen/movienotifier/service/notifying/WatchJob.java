package it.sijmen.movienotifier.service.notifying;

import it.sijmen.movienotifier.service.cinemas.CinemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WatchJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchJob.class);

    private CinemaService cinemaService;

    public WatchJob(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Scheduled(cron = "0 */10 22-23 * * *")
    @Scheduled(cron = "0 */10 0-6 * * *")
    public void executeByNight(){
        LOGGER.info("Executing night job");
        cinemaService.checkCinemasForChangesAndNotifyWatchers();
    }

    @Scheduled(cron = "0 */3 8-21 * * *")
    public void executeByDay(){
        LOGGER.info("Executing day job");
        cinemaService.checkCinemasForChangesAndNotifyWatchers();
    }

}
