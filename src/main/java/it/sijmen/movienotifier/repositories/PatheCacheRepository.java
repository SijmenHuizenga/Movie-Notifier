package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.PatheMovieCache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatheCacheRepository extends MongoRepository<PatheMovieCache, String> {

  public PatheMovieCache getFirstByMovieid(int movieId);
}
