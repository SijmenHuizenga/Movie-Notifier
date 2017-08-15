package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.ReadAllListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ReadAllActor<T extends Model> extends Actor<T, ReadAllListener<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadAllActor.class);

    public ReadAllActor(Class<? extends T> modelClass, ReadAllListener<T> listener, ObjectMapper mapper, MongoRepository<T, String> repository) {
        super(modelClass, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.GET && request.getUrldata() == null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        listener.checkReadAllRequest(request);
        List<T> result = repository.findAll();
        if(!listener.allowReadAll(request, result)) {
            LOGGER.warn("Not authorized to read all {}. Request: {}", modelClass.getSimpleName(), request);
            throw new UnauthorizedException();
        }
        LOGGER.trace("Read all {}. Request: {}", modelClass.getSimpleName(), request);
        return ResponseEntity.ok(result);
    }
}
