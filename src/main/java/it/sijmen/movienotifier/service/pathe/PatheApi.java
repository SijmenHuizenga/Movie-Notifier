package it.sijmen.movienotifier.service.pathe;

import static it.sijmen.movienotifier.model.FilterOption.NOPREFERENCE;
import static it.sijmen.movienotifier.model.FilterOption.YES;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.sijmen.movienotifier.model.FilterOption;
import it.sijmen.movienotifier.model.PatheMovieCache;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import it.sijmen.movienotifier.repositories.PatheCacheRepository;
import it.sijmen.movienotifier.service.CinemaService;
import it.sijmen.movienotifier.service.NotificationService;
import it.sijmen.movienotifier.service.pathe.api.PatheShowing;
import it.sijmen.movienotifier.service.pathe.api.PatheShowings;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PatheApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(PatheApi.class);

  private ObjectMapper mapper;
  private String patheApiKey;
  private PatheCacheRepository repository;
  private NotificationService notificationService;

  @Autowired
  public PatheApi(
      ObjectMapper mapper,
      @Value("${cinema.pathe.apikey}") String patheApiKey,
      PatheCacheRepository repository,
      NotificationService notificationService) {
    this.mapper = mapper;
    this.patheApiKey = patheApiKey;
    this.repository = repository;
    this.notificationService = notificationService;
  }

  private PatheShowings getShowingsPerCinema(int movieId) throws IOException {
    String uri = "https://connect.pathe.nl/v1/movies/" + movieId + "/schedules";
    HttpResponse<String> stringHttpResponse = makeGetRequest(uri);

    if (stringHttpResponse.getStatus() != 200)
      throw new IOException(
          "Status returned " + stringHttpResponse.getStatus() + " after request " + uri);
    PatheShowings patheShowings =
        mapper.readValue(stringHttpResponse.getBody(), PatheShowings.class);
    if (patheShowings == null) throw new IOException("Unexpected api result");
    patheShowings.setMovieid(movieId);
    return patheShowings;
  }

  public HttpResponse<String> makeGetRequest(String uri) throws IOException {
    try {
      return Unirest.get(uri).header("X-Client-Token", patheApiKey).asString();
    } catch (UnirestException e) {
      throw new IOException("Could not load request.", e);
    }
  }

  public void checkWatcher(List<Watcher> watcher) {
    LOGGER.trace("Checking #{} watchers", watcher.size());
    watcher.stream()
        .collect(Collectors.groupingBy(Watcher::getMovieid))
        .forEach(this::checkForUpdates);
  }

  private void checkForUpdates(int movieId, List<Watcher> watchers) {
    LOGGER.trace("Checking #{} watchers with modieid {}", watchers.size(), movieId);
    PatheMovieCache oldData;
    PatheShowings newData;
    try {
      oldData = repository.getFirstByMovieid(movieId);
      newData = this.getShowingsPerCinema(movieId);
    } catch (Exception e) {
      LOGGER.error("Could not load old or new data for movieId {}", movieId, e);
      return;
    }
    if (oldData == null) {
      repository.save(makeCacheFromResponse(newData));
      LOGGER.trace("First time retreving data for movie {} and storing in repo", movieId);
      return;
    }
    if (newData.getShowingsids().isEmpty()) {
      LOGGER.trace(
          "Received no showings for movieid {} so nothing to do for this movieid", movieId);
      return;
    }
    if (oldData.getShowingids().containsAll(newData.getShowingsids())) {
      LOGGER.trace("Old and new data for movie {} are equal", movieId);
      return;
    }
    repository.save(makeCacheFromResponse(newData));
    LOGGER.trace("Stored new data for movie {}", movieId);

    List<PatheShowing> showings = newData.getShowings();
    List<Long> oldshowings = oldData.getShowingids();

    showings.removeIf(s -> oldshowings.contains(s.getId()));
    watchers
        .parallelStream()
        .forEach(
            w -> {
              List<PatheShowing> collect =
                  showings.stream()
                      .filter(showing -> accepts(w, showing))
                      .collect(Collectors.toList());
              if (!collect.isEmpty()) {
                try {
                  notificationService.sendUpdates(w, collect);
                } catch (Exception e) {
                  LOGGER.error(e.getMessage());
                }
              }
            });
  }

  private PatheMovieCache makeCacheFromResponse(PatheShowings newData) {
    return new PatheMovieCache(newData.getMovieid(), newData.getShowingsids());
  }

  public boolean accepts(Watcher watcher, PatheShowing showing) {
    WatcherFilters d = watcher.getFilters();
    if (showing.getCinemaId() != watcher.getFilters().getCinemaid()) {
      LOGGER.debug(
          "Watcher's cinemaid {} does not equal showing cinemaid {}",
          watcher.getFilters().getCinemaid(),
          showing.getCinemaId());
      return false;
    }
    if (!(showing.getStart() <= d.getStartbefore())) {
      LOGGER.debug("End does not do good");
      return false;
    }
    if (!(showing.getStart() >= d.getStartafter())) {
      LOGGER.debug("Start does not do good");
      return false;
    }
    if (showing.getMovieId() != watcher.getMovieid()) {
      LOGGER.debug("Movie id is not equal!");
      return false;
    }

    return accepts(d.isD3(), toBool(showing.getIs3d()))
        && accepts(d.isImax(), toBool(showing.getImax()))
        && accepts(d.isOv(), toBool(showing.getOv()))
        && accepts(d.isNl(), toBool(showing.getNl()))
        && accepts(d.isHfr(), toBool(showing.getHfr()))
        && accepts(d.isK4(), toBool(showing.getIs4k()))
        && accepts(d.isDx4(), showing.getIs4dx())
        && accepts(d.isDolbycinema(), showing.getIsVision())
        &&

        /*
         * A movie is shown using the Dolby Atmos sound system when one of the following conditions are true:
         *  - the `isAtmos` property is set on the PatheShowing
         *  - the showing takes place in a Dolby Cinema room
         */
        accepts(d.isDolbyatmos(), toBool(showing.getIsAtmos()), showing.getIsVision())
        &&

        /*
         * A movie is projected using a laser-projector if one of the following conditions are true:
         *   - the `isLaser` property is set on the PatheShowing
         *   - the showing takes places in a DolbyCinema cinema room
         *   - the showing takes place in a IMAX room that has an Laser projector
         */
        accepts(
            d.isLaser(),
            toBool(showing.getIsLaser()),
            showing.getIsVision(),
            (showing.getImax() == 1 && CinemaService.hasLaserImax(showing.getCinemaId())));
  }

  private Boolean toBool(Integer i) {
    if (i == null) {
      return null;
    }
    return i == 1;
  }

  /**
   * If option is NOPREFERENCE, always return true If one of the value is null, return true If
   * option is YES, all value's are or'ed: (A || B || C ...) If option is NO, all values are inverse
   * or'ed: !(A || B || C ...)
   */
  private boolean accepts(FilterOption option, Boolean... value) {
    if (option == NOPREFERENCE) {
      return true;
    }
    boolean result = false;

    for (Boolean b : value) {
      // if there is missing data, always return true
      if (b == null) {
        return true;
      }
      if (b) {
        result = true;
      }
    }

    if (option == YES) {
      return result;
    }

    return !result;
  }
}
