package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaService.class);

    private List<Cinema> cinemas;

    private WatcherRepository repository;

    public CinemaService(List<Cinema> cinemas, WatcherRepository repository) {
        this.cinemas = cinemas;
        this.repository = repository;
    }

    public void checkCinemasForChangesAndNotifyWatchers(){
        long now = System.currentTimeMillis();
        List<Watcher> all = repository.getAllByBeginIsLessThanAndEndIsGreaterThan(now, now);
        LOGGER.trace("Checking #{} watchers.", all.size());
        all.stream().collect(Collectors.groupingBy(Watcher::getCinemaPrefix))
                .forEach(this::checkUpdates);
    }

    private void checkUpdates(String cinemaprefix, List<Watcher> watchers) {
        LOGGER.trace("Checking #{} watchers with cinemaprefix {}", watchers.size(), cinemaprefix);
        for(Cinema c : cinemas){
            if(cinemaprefix.equals(c.getCinemaIdPrefix())){
                c.checkWatcher(watchers);
                return;
            }
        }
        LOGGER.warn("Could not watch cinema {} since there is no cinema configuration available for this id.", cinemaprefix);
    }

}
