package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.service.cinemas.pathe.PatheMoviesResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Singleton;

@Singleton
public interface PatheCacheRepository extends MongoRepository<PatheMoviesResponse, String> {

    public PatheMoviesResponse getFirstByMovieid(int movieId);

}
