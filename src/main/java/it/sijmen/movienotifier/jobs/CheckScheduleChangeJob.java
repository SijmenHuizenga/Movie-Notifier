package it.sijmen.movienotifier.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Sijmen on 10-4-2017.
 */

public class CheckScheduleChangeJob implements org.quartz.Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        System.out.println("Executed!");

    }
}
