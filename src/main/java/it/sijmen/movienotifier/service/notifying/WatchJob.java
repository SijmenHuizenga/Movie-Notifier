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

    //todo: this random thing doesnt work
    @Scheduled(cron = "${random.int[0,59]} */12  22-23 * * *")
    @Scheduled(cron = "${random.int[0,59]} */12  0-6 * * *")
    public void executeByNight(){
        LOGGER.info("Executing night job");
        cinemaService.checkCinemasForChangesAndNotifyWatchers();
    }

    @Scheduled(cron = "${random.int[0,59]} */5 6-21 * * *")
    public void executeByDay(){
        LOGGER.info("Executing day job");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cinemaService.checkCinemasForChangesAndNotifyWatchers();
    }

}
