package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.CreateListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class CreateActor<T extends Model> extends Actor<T, CreateListener<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateActor.class);

    public CreateActor(Class<? extends T> modelClass, CreateListener<T> listener, ObjectMapper mapper,
                       MongoRepository<T, String> repository) {
        super(modelClass, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.PUT && request.getUrldata() == null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        listener.checkCreateRequest(request);

        T model = readModelFromBody(this.modelClass, request.getBody());

        if(!listener.allowCreate(request, model)) {
            LOGGER.warn("Not allowed to create %s with request %s", modelClass.getSimpleName(), request);
            throw new UnauthorizedException();
        }

        model = listener.beforeCreateValidation(model);
        model.validate();

        model = listener.beforeCreateStore(model);
        repository.save(model);

        LOGGER.trace("Model stored in repository %s. Model: %s", modelClass.getSimpleName(), model);

        return ResponseEntity.ok(model);
    }
}
