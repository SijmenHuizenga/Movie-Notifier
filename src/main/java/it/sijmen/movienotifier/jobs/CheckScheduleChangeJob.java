package it.sijmen.movienotifier.jobs;

import it.sijmen.movienotifier.pathe.api.PatheApi;
import it.sijmen.movienotifier.pathe.dto.MovieSchedulePerCinema;
import it.sijmen.movienotifier.pathe.dto.PatheSchedule;
import it.sijmen.movienotifier.str.NotifierConfiguration;
import it.sijmen.movienotifier.str.NewScheduleListener;
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
    @Named("storage-folder")
    private String storageFolder;

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
            e.printStackTrace();
            //todo: handle error
            return;
        }
        List<PatheSchedule> newSchedules = filterNewSchedules(oldData, newData);

        sendUpdates(scheduleListeners, newSchedules);

        try {
            saveLastMovieData(movieKey, newData);
        } catch (IOException e) {
            e.printStackTrace();
            //todo: handle error!
            //if this goes wrong than possible people get the same message again! This is wrong!
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
        //todo
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
    }

    private File getStoragePath(){
        return new File(storageFolder);
    }

    private File getMovieStorageFile(int movieKey){
        return new File(getStoragePath(), movieKey + ".json");
    }
}
