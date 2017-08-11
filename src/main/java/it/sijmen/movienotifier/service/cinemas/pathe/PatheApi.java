package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherDetails;
import it.sijmen.movienotifier.repositories.PatheCacheRepository;
import it.sijmen.movienotifier.service.cinemas.Cinema;
import it.sijmen.movienotifier.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Service
public class PatheApi extends Cinema {

    private SimpleDateFormat format1 = new SimpleDateFormat("EEE d MMMM HH:mm");
    private SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

    private ObjectMapper mapper;
    private String patheApiKey;
    private PatheCacheRepository repository;
    private NotificationService notificationService;

    @Inject
    public PatheApi(ObjectMapper mapper, @Value("${cinema.pathe.apikey}") String patheApiKey, PatheCacheRepository repository, NotificationService notificationService) {
        this.mapper = mapper;
        this.patheApiKey = patheApiKey;
        this.repository = repository;
        this.notificationService = notificationService;
    }

    private PatheMoviesResponse getShowingsPerCinema(int movieId) throws IOException {
        String uri = "https://connect.pathe.nl/v1/movies/"+movieId+"/schedules";
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = Unirest.get(uri)
                    .header("X-Client-Token", patheApiKey)
                    .asString();
        } catch (UnirestException e) {
            throw new IOException("Could not load request.", e);
        }
        if(stringHttpResponse.getStatus() != 200)
            throw new IOException("Status returned " + stringHttpResponse.getStatus() + " after request " + uri);
        PatheMoviesResponse patheMoviesResponse = mapper.readValue(stringHttpResponse.getBody(), PatheMoviesResponse.class);
        patheMoviesResponse.setMovieid(movieId);
        return patheMoviesResponse;
    }

    @Override
    public String getCinemaIdPrefix() {
        return "PATHE";
    }

    @Override
    public void checkWatcher(List<Watcher> watcher) {
        watcher.stream().collect(Collectors.groupingBy(Watcher::getMovieid))
                .forEach(this::checkForUpdates);
    }

    private void checkForUpdates(int movieId, List<Watcher> watchers) {
        PatheMoviesResponse oldData;
        PatheMoviesResponse newData;
        try {
            oldData = repository.getFirstByMovieid(movieId);
            newData = this.getShowingsPerCinema(movieId);
        } catch (Exception e) {
            //todo: handle error
            e.printStackTrace();
            return;
        }
        if(oldData.equals(newData))
            return;
        repository.save(newData);
        watchers.forEach(w -> this.sendUpdates(w, newData));
    }

    private void sendUpdates(Watcher watcher, PatheMoviesResponse showings) {
        for(PatheShowing showing : showings.getShowings())
            if(accepts(watcher, showing))
                notificationService.notify(watcher.getUser(), makeMessage(watcher, showing));
    }

    private boolean accepts(Watcher watcher, PatheShowing showing){
        int realWatcherCinemaId = Integer.parseInt(watcher.getCinemaid().substring(getCinemaIdPrefix().length()));
        WatcherDetails d = watcher.getProps();
        return showing.getStart().getTimeInMillis() < watcher.getStartBefore() &&
                showing.getStart().getTimeInMillis() > watcher.getStartAfter() &&
                showing.getCinemaId() == realWatcherCinemaId &&
                eq(d.isD3(), showing.getIs3d()) &&
                eq(d.isImax(), showing.getImax()) &&
                eq(d.isOv(), showing.getOv()) &&
                eq(d.isNl(), showing.getNl()) &&
                eq(d.isHfr(), showing.getHfr()) &&
                eq(d.isDolbyatmos(), showing.getIsAtmos()) &&
                eq(d.isK4(), showing.getIs4k()) &&
                eq(d.isLaser(), showing.getIsLaser());
    }

    private boolean eq(Boolean expected, int actual) {
        return expected == null || expected == (actual == 1);
    }

    private String makeMessage(Watcher watcher, PatheShowing showing){
        StringBuilder builder = new StringBuilder(watcher.getName());

        if(showing.getImax() == 1)
            builder.append(" IMAX");
        if(showing.getIs3d() == 1)
            builder.append(" 3D");
        if(showing.getIs4k() == 1)
            builder.append(" 4K");
        if(showing.getIsLaser() == 1)
            builder.append(" LASER");

        builder.append(System.lineSeparator());
        if(showing.getStart() != null)
            builder.append(format1.format(showing.getStart().getTime())).append(" - ")
                    .append(format2.format(showing.getEnd().getTime()))
                    .append(System.lineSeparator());
        builder.append("https://www.pathe.nl/tickets/start/")
                .append(showing.getId());

        return builder.toString();
    }
}
