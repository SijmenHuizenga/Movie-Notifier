package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.PatheMovieCache;
import it.sijmen.movienotifier.service.cinemas.pathe.PatheMoviesResponse;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Singleton;

@Singleton
public interface PatheCacheRepository extends MongoRepository<PatheMovieCache, String> {

    public PatheMovieCache getFirstByMovieid(int movieId);

}
