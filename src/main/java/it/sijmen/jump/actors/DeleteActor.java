package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.DeleteListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class DeleteActor<T extends Model> extends Actor<T, DeleteListener<T>> {

    public DeleteActor(Class<? extends T> modelClass, DeleteListener<T> listener, ObjectMapper mapper, MongoRepository<T, String> repository) {
        super(modelClass, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.DELETE && request.getUrldata() != null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        listener.checkDeleteRequest(request);
        T toDelete = repository.findOne(request.getUrldata());
        if(toDelete == null)
            throw new UnauthorizedException();
        if(!listener.allowDelete(request, toDelete))
            throw new UnauthorizedException();
        repository.delete(toDelete);
        return ResponseEntity.ok().build();
    }
}
