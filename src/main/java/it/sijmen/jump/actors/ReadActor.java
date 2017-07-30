package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.ReadListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ReadActor<T extends Model> extends Actor<T, ReadListener<T>> {

    public ReadActor(Class<? extends T> modelClass, ReadListener<T> listener, ObjectMapper mapper, MongoRepository<T, String> repository) {
        super(modelClass, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.GET && request.getUrldata() != null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        T result = repository.findOne(request.getUrldata());
        if(!listener.allowRead(getApiKey(request.getHeaders()), result))
            throw new UnauthorizedException();
        return ResponseEntity.ok(result);
    }
}
