package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.CreateListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class CreateActor<T extends Model> extends Actor<T, CreateListener<T>> {


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
        T model = readModelFromBody(this.modelClass, request.getBody());

        if(!listener.allowCreation(getApiKey(request.getHeaders()), model))
            throw new UnauthorizedException();

        model = listener.beforeCreationValidation(model);
        model.validate();

        model = listener.beforeCreationStore(model);
        repository.save(model);

        //todo replace with .created(location) ???
        return ResponseEntity.ok(model);
    }
}
