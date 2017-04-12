package it.sijmen.movienotifier.jobs;

import it.sijmen.movienotifier.ErrorLogger;
import it.sijmen.movienotifier.notifiers.FBMessengerNotifier;
import it.sijmen.movienotifier.notifiers.NotificationException;
import it.sijmen.movienotifier.pathe.api.PatheApi;
import it.sijmen.movienotifier.pathe.dto.MovieSchedulePerCinema;
import it.sijmen.movienotifier.pathe.dto.PatheSchedule;
import it.sijmen.movienotifier.str.NotifierConfiguration;
import it.sijmen.movienotifier.str.NewScheduleListener;
import it.sijmen.movienotifier.str.Recipient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sijmen on 10-4-2017.
 */

public class CheckScheduleChangeJob implements org.quartz.Job {

    @Inject
    private PatheApi api;

    @Inject
    private NotifierConfiguration configuration;

    @Inject
    private FBMessengerNotifier fbMessengerNotifier;

    @Inject
    @Named("storage-folder")
    private String storageFolder;

    @Inject
    private ErrorLogger errorHandler;

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        HashMap<Integer, List<NewScheduleListener>> listeners = configuration.getListeners();
        for(Integer movieKey : listeners.keySet())
            checkUpdates(movieKey, listeners.get(movieKey));
    }

    private void checkUpdates(int movieKey, List<NewScheduleListener> scheduleListeners) {
        MovieSchedulePerCinema oldData, newData;
        try {
            newData = api.getMovieSchedulePerCinema(movieKey);
            oldData = loadLastMovieData(movieKey);
        } catch (IOException e) {
            errorHandler.log("Could not load newdata or old data. ", e);
            return;
        }
        List<PatheSchedule> newSchedules = filterNewSchedules(oldData, newData);

        sendUpdates(scheduleListeners, newSchedules);

        try {
            saveLastMovieData(movieKey, newData);
        } catch (IOException e) {
            errorHandler.log("Could not save new move state file!", e);
        }
    }

    private void sendUpdates(List<NewScheduleListener> listeners, List<PatheSchedule> schedules) {
        for(PatheSchedule schedule : schedules){
            for(NewScheduleListener listener : listeners){
                if(listener.accepts(schedule)){
                    notifyRecipients(listener.getRecipients(), schedule);
                }
            }
        }
    }

    private void notifyRecipients(ArrayList<String> recipients, PatheSchedule schedule) {
        for(String r : recipients){
            Recipient recipient = configuration.getRecipient(r);
            try {
                fbMessengerNotifier.notify(recipient,
                        "New movie online!!! https://www.pathe.nl/tickets/start/"+schedule.getId());
            } catch (NotificationException e) {
                errorHandler.log("Failed to notify " + recipient + " about movie " + schedule.getId(), e);
            }
        }
    }

    private List<PatheSchedule> filterNewSchedules(MovieSchedulePerCinema oldData, MovieSchedulePerCinema newData){
        if(oldData == null)
            return newData.getSchedules();
        ArrayList<PatheSchedule> clone = new ArrayList<>(newData.getSchedules());
        clone.removeAll(oldData.getSchedules());
        return clone;
    }

    private MovieSchedulePerCinema loadLastMovieData(int movieKey) throws IOException {
        File file = getMovieStorageFile(movieKey);
        if(!file.exists())
            return null;
        return api.parse(new String(Files.readAllBytes(file.toPath())), MovieSchedulePerCinema.class);
    }

    private void saveLastMovieData(int movieKey, MovieSchedulePerCinema data) throws IOException {
        File file = getMovieStorageFile(movieKey);
        FileWriter f2 = new FileWriter(file, false);
        f2.write(api.encode(data));
        f2.flush();
        f2.close();
    }

    private File getStoragePath(){
        return new File(storageFolder);
    }

    private File getMovieStorageFile(int movieKey){
        return new File(getStoragePath(), movieKey + ".json");
    }
}
