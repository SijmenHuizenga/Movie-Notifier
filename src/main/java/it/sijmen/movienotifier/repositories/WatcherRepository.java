package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.Watcher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatcherRepository extends MongoRepository<Watcher, String> {

    public int countDistinctByUserAndMovieid(String user, int movieId);

}
