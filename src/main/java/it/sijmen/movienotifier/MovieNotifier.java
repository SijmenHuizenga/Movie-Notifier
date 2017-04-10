package it.sijmen.movienotifier;


import it.sijmen.movienotifier.inj.JobFactory;
import it.sijmen.movienotifier.jobs.CheckScheduleChangeJob;
import it.sijmen.movienotifier.jobs.DayNightCalendar;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Inject;
import java.time.LocalTime;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Hello world!
 */
public class MovieNotifier {

    @Inject
    JobFactory jobFactory;

    public void run() {
        try {
            startJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
            //todo
        }
    }

    private void startJobs() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(jobFactory);

        JobDetail job = JobBuilder.newJob(CheckScheduleChangeJob.class)
                .withIdentity("CheckScheduleChangeJob")
                .storeDurably()
                .build();
        scheduler.addJob(job, true);
        
        scheduler.addCalendar("night",
                new DayNightCalendar(LocalTime.parse("22:00:00"), LocalTime.parse("07:00:00")), false, false);

        scheduler.addCalendar("day",
                new DayNightCalendar(LocalTime.parse("07:00:00"), LocalTime.parse("22:00:00")), false, false);

        Trigger dayTrigger = newTrigger()
                .withIdentity("DayTrigger")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(30).repeatForever())
                .modifiedByCalendar("day")
                .forJob(job)
                .build();

        Trigger nightTrigger = newTrigger()
                .withIdentity("NightTrigger")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInMinutes(5).repeatForever())
                .modifiedByCalendar("night")
                .forJob(job)
                .build();

        scheduler.scheduleJob(dayTrigger);
        scheduler.scheduleJob(nightTrigger);

        scheduler.start();
    }
}
