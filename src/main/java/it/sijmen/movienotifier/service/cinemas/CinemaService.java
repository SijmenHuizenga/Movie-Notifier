package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
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
        all.removeIf(w -> !runningNow(w.getBegin(), w.getEnd()));
        all.stream().collect(Collectors.groupingBy(w -> w.getFilters().getCinemaid()))
                .forEach(this::checkUpdates);
    }

    public static boolean runningNow(long begin, long end) {
        return runningNow(System.currentTimeMillis(), begin, end);
    }

    public static boolean runningNow(long now, long begin, long end){
        return begin <= now && now <= end;
    }


    private void checkUpdates(String cinemaId, List<Watcher> watchers) {
        for(Cinema c : cinemas){
            if(cinemaId.startsWith(c.getCinemaIdPrefix())){
                c.checkWatcher(watchers);
                return;
            }
        }
        LOGGER.warn("Could not watch cinema {} since there is no cinema configuration available for this id.", cinemaId);
    }

}
