package it.sijmen.movienotifier.repositories;

import it.sijmen.movienotifier.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findById(String id);
    public List<User> findByApikey(String apiKey);
    public int countDistinctByName(String name);
    public int countDistinctByEmail(String name);
}