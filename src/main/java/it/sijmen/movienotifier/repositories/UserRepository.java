package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User getFirstByUuid(String id);
    public User findFirstByApikey(String apiKey);
    public User findFirstByName(String name);
    public List<User> getAllByName(String name);
    public List<User> getAllByEmail(String name);
}