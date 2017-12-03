package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.sijmen.movienotifier.model.FilterOption;
import it.sijmen.movienotifier.model.PatheMovieCache;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import it.sijmen.movienotifier.repositories.PatheCacheRepository;
import it.sijmen.movienotifier.service.cinemas.Cinema;
import it.sijmen.movienotifier.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static it.sijmen.movienotifier.model.FilterOption.NO;
import static it.sijmen.movienotifier.model.FilterOption.NOPREFERENCE;
import static it.sijmen.movienotifier.model.FilterOption.YES;

@Singleton
@Service
public class PatheApi implements Cinema {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatheApi.class);

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
        HttpResponse<String> stringHttpResponse = makeGetRequest(uri);

        if(stringHttpResponse.getStatus() != 200)
            throw new IOException("Status returned " + stringHttpResponse.getStatus() + " after request " + uri);
        PatheMoviesResponse patheMoviesResponse = mapper.readValue(stringHttpResponse.getBody(), PatheMoviesResponse.class);
        if(patheMoviesResponse == null)
            throw new IOException("Unexpected api result");
        patheMoviesResponse.setMovieid(movieId);
        return patheMoviesResponse;
    }

    public HttpResponse<String> makeGetRequest(String uri) throws IOException {
        try{
            return Unirest.get(uri)
                    .header("X-Client-Token", patheApiKey)
                    .asString();
        } catch (UnirestException e) {
            throw new IOException("Could not load request.", e);
        }
    }

    @Override
    public String getCinemaIdPrefix() {
        return "PATHE";
    }

    @Override
    public void checkWatcher(List<Watcher> watcher) {
        LOGGER.trace("Checking #{} watchers", watcher.size());
        watcher.stream()
                .collect(Collectors.groupingBy(Watcher::getMovieid))
                .forEach(this::checkForUpdates);
    }

    private void checkForUpdates(int movieId, List<Watcher> watchers) {
        LOGGER.trace("Checking #{} watchers with modieid {}", watchers.size(), movieId);
        PatheMovieCache oldData;
        PatheMoviesResponse newData;
        try {
            oldData = repository.getFirstByMovieid(movieId);
            newData = this.getShowingsPerCinema(movieId);
        } catch (Exception e) {
            LOGGER.error("Could not load old or new data for movieId {}", movieId, e);
            return;
        }
        if(oldData == null){
            repository.save(makeCacheFromResponse(newData));
            LOGGER.trace("First time retreving data for movie {} and storing in repo", movieId);
            return;
        }
        if(newData.getShowingsids().isEmpty()){
            LOGGER.trace("Received no showings for movieid {} so nothing to do for this movieid", movieId);
            return;
        }
        if(oldData.getShowingids().containsAll(newData.getShowingsids())) {
            LOGGER.trace("Old and new data for movie {} are equal", movieId);
            return;
        }
        repository.save(makeCacheFromResponse(newData));
        LOGGER.trace("Stored new data for movie {}", movieId);

        List<PatheShowing> showings = newData.getShowings();
        List<Long> oldshowings =  oldData.getShowingids();

        showings.removeIf(s -> oldshowings.contains(s.getId()));
        watchers.forEach(w -> this.sendUpdates(w, showings));
    }

    private PatheMovieCache makeCacheFromResponse(PatheMoviesResponse newData){
        return new PatheMovieCache(newData.getMovieid(), newData.getShowingsids());
    }

    private void sendUpdates(Watcher watcher, List<PatheShowing> showings) {
        for(PatheShowing showing : showings)
            if(accepts(watcher, showing)) {
                LOGGER.trace("Watcher accepts Showing so now notifying user");
                notificationService.notify(watcher.getUserid(), makeMessage(watcher, showing));
            }
    }

    public boolean accepts(Watcher watcher, PatheShowing showing){
        int realWatcherCinemaId = Integer.parseInt(watcher.getFilters().getCinemaid().substring(getCinemaIdPrefix().length()));
        WatcherFilters d = watcher.getFilters();
        if(showing.getCinemaId() != realWatcherCinemaId){
            LOGGER.debug("Cinema id does not equal");
            return false;
        }
        if(!(showing.getStart() <= d.getStartbefore())){
            LOGGER.debug("End does not do good");
            return false;
        }
        if(!(showing.getStart() >= d.getStartafter())){
            LOGGER.debug("Start does not do good");
            return false;
        }
        if(showing.getMovieId() != watcher.getMovieid()){
            LOGGER.debug("Movie id is not equal!");
            return false;
        }

        if(     !eq(d.isD3(), showing.getIs3d()) ||
                !eq(d.isImax(), showing.getImax()) ||
                !eq(d.isOv(), showing.getOv()) ||
                !eq(d.isNl(), showing.getNl()) ||
                !eq(d.isHfr(), showing.getHfr()) ||
                !eq(d.isDolbyatmos(), showing.getIsAtmos()) ||
                !eq(d.isK4(), showing.getIs4k()) ||
                !eq(d.isLaser(), showing.getIsLaser()) ||
                !eqBool(d.isDx4(), showing.getIs4dx())
                ){
            LOGGER.debug("The boolean filters failed");
            return false;
        }
        return true;
    }

    public boolean eq(FilterOption expected, Integer actual) {
        return expected == NOPREFERENCE
                || actual == null
                || (expected == YES && (actual == 1))
                || (expected == NO && (actual == 0));
    }

    public boolean eqBool(FilterOption expected, Boolean actual) {
        return expected == NOPREFERENCE
                || actual == null
                || (expected == YES && actual)
                || (expected == NO && !actual);
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
        if(showing.getIs4dx())
            builder.append(" 4DX");

        builder.append(System.lineSeparator());
        if(showing.getStart() != -1L)
            builder.append(format1.format(new Date(showing.getStart()))).append(" - ")
                    .append(format2.format(new Date(showing.getEnd())))
                    .append(System.lineSeparator());
        builder.append("https://www.pathe.nl/tickets/start/")
                .append(showing.getId());

        return builder.toString();
    }
}
