package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private static List<CinemaLocation> allCinemas;

    static {
        allCinemas = new ArrayList<>();

        allCinemas.add(new CinemaLocation("PATHE9", "Pathé Arena", 52.31233f, 4.94577f));
        allCinemas.add(new CinemaLocation("PATHE2", "Pathé Tuschinski", 52.36655f, 4.89469f));
        allCinemas.add(new CinemaLocation("PATHE1", "Pathé City", 52.36345f, 4.88383f));
        allCinemas.add(new CinemaLocation("PATHE10", "Pathé De Munt", 52.36648f, 4.89342f));
        allCinemas.add(new CinemaLocation("PATHE12", "Pathé De Kuip", 51.8968f, 4.5231f));
        allCinemas.add(new CinemaLocation("PATHE6", "Pathé Schouwburgplein", 51.92078f, 4.47341f));
        allCinemas.add(new CinemaLocation("PATHE13", "Pathé Spuimarkt", 52.07727f, 4.31522f));
        allCinemas.add(new CinemaLocation("PATHE5", "Pathé Buitenhof", 52.07858f, 4.31089f));
        allCinemas.add(new CinemaLocation("PATHE7", "Pathé Scheveningen", 52.11241f, 4.2839f));
        allCinemas.add(new CinemaLocation("PATHE3", "Pathé Rembrandt Utrecht", 52.09388f, 5.11629f));
        allCinemas.add(new CinemaLocation("PATHE30", "CineMec Utrecht", 52.09684f, 5.07095f));
        allCinemas.add(new CinemaLocation("PATHE23", "Pathé Amersfoort", 52.15876f, 5.3808f));
        allCinemas.add(new CinemaLocation("PATHE27", "Pathé Arnhem", 51.98422f, 5.90339f));
        allCinemas.add(new CinemaLocation("PATHE20", "Pathé Breda", 51.58972f, 4.78503f));
        allCinemas.add(new CinemaLocation("PATHE18", "Pathé Delft", 52.00861f, 4.36329f));
        allCinemas.add(new CinemaLocation("PATHE8", "Pathé Eindhoven", 51.44082f, 5.48053f));
        allCinemas.add(new CinemaLocation("PATHE4", "Pathé Groningen", 53.21434f, 6.56636f));
        allCinemas.add(new CinemaLocation("PATHE22", "Pathé Haarlem", 52.38176f, 4.6294f));
        allCinemas.add(new CinemaLocation("PATHE11", "Pathé Helmond", 51.47793f, 5.65092f));
        allCinemas.add(new CinemaLocation("PATHE17", "Pathé Maastricht", 50.85643f, 5.68988f));
        allCinemas.add(new CinemaLocation("PATHE19", "Pathé Tilburg", 51.5579f, 5.08961f));
        allCinemas.add(new CinemaLocation("PATHE14", "Pathé Zaandam", 52.43842f, 4.81777f));
        allCinemas.add(new CinemaLocation("PATHE28", "Pathé Zwolle", 52.514f, 6.08677f));
        allCinemas.add(new CinemaLocation("PATHE29", "CineMec Ede", 52.01575f, 5.64801f));
        allCinemas.add(new CinemaLocation("PATHE31", "CineMec Nijmegen", 51.87994f, 5.862f));
        allCinemas.add(new CinemaLocation("PATHE32", "Tivoli en Cinema Leeuwarden", 53.20119f, 5.79235f));
    }

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

    public List<CinemaLocation> getAllCinemaLocations(){
        return allCinemas;
    }

}

