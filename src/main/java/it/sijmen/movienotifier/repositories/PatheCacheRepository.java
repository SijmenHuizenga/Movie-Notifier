package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.service.cinemas.pathe.PatheMoviesResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatheCacheRepository extends MongoRepository<PatheMoviesResponse, String> {

    public PatheMoviesResponse getFirstByMovieid(int movieId);

}
