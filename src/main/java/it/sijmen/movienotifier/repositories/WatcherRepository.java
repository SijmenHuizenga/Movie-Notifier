package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.Watcher;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public interface WatcherRepository extends MongoRepository<Watcher, String> {

    public Watcher getFirstById(String id);
    public List<Watcher> getAllByUser(String user);

}
