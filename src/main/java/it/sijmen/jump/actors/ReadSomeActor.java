package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.ReadSomeListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ReadSomeActor<T extends Model> extends Actor<T, ReadSomeListener<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadSomeActor.class);

    public ReadSomeActor(Class<? extends T> modelClass, ReadSomeListener<T> listener, ObjectMapper mapper,
                         MongoRepository<T, String> repository) {
        super(modelClass, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.GET && request.getUrldata() == null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        listener.checkReadSomeRequest(request);
        List<T> result = listener.getReadSomeResult(request);
        if(result == null) {
            LOGGER.warn("No result from readSome thus unauthorized", request);
            throw new UnauthorizedException();
        }

        LOGGER.trace("Read some " + modelClass.getSimpleName(), request);
        return ResponseEntity.ok(result);
    }
}
