package it.sijmen.jump.listeners;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorListener<T> {

    default T getById(MongoRepository<T, String> repository, String id) {return repository.findOne(id);}

}
