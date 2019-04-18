package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.Watcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatcherRepository extends MongoRepository<Watcher, String> {

    public Watcher getFirstByUuid(String id);
    public List<Watcher> getAllByUserid(String user);
    public List<Watcher> getAllByBeginIsLessThanAndEndIsGreaterThan(long beginLessThan, long endGreaterThan);
    public void deleteWatchersByUserid(String user);

}
