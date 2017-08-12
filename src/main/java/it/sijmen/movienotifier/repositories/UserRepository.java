package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public interface UserRepository extends MongoRepository<User, String> {

    public User findFirstByApikey(String apiKey);
    public User findFirstByName(String name);
    public List<User> getAllByName(String name);
    public List<User> getAllByEmail(String name);
}