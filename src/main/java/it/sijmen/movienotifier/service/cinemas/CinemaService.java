package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        List<Watcher> all = repository.findAll();
        all.removeIf(w -> w.getStartBefore() < System.currentTimeMillis());
        all.stream().collect(Collectors.groupingBy(Watcher::getCinemaid))
                .forEach(this::checkUpdates);
    }

    private void checkUpdates(String cinemaId, List<Watcher> watchers) {
        for(Cinema c : cinemas){
            if(cinemaId.startsWith(c.getCinemaIdPrefix())){
                c.checkWatcher(watchers);
                return;
            }
        }
        LOGGER.warn("Could not watch cinema " + cinemaId + " since there is no cinema configuration available for this id.", watchers);
    }

}
