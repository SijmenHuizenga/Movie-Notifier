package it.sijmen.movienotifier.inj;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Created by Sijmen on 10-4-2017.
 */
public class JobFactory implements org.quartz.spi.JobFactory {

    private final Injector guice;

    @Inject
    public JobFactory(final Injector guice) {
        this.guice = guice;
    }

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = triggerFiredBundle.getJobDetail();
        Class jobClass = jobDetail.getJobClass();

        try {
            return (Job) guice.getInstance(jobClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }
}