package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.Watcher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WatcherRepository extends MongoRepository<Watcher, String> {

    public List<Watcher> getAllByUser(String user);

}
