package it.sijmen.jump;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.actors.*;
import it.sijmen.jump.listeners.*;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Jump<T extends Model> {

    private final ObjectMapper mapper;
    private final MongoRepository<T, String> repository;
    private Class<T> modelClass;

    private final List<Actor<T, ?>> actors = new ArrayList<>();

    @Inject
    public Jump(@NotNull ObjectMapper mapper,
                @NotNull MongoRepository<T, String> repository,
                @NotNull Class<T> modelClass) {
        this.mapper = mapper;
        this.repository = repository;
        this.modelClass = modelClass;
    }

    public HttpEntity<?> handle(JumpRequest request) {
        for(Actor actor : actors){
            if(actor.accepts(request))
                return actor.handle(request);
        }
        throw new BadRequestException();
    }

    public void enableCreate(CreateListener<T> listener){
        actors.add(new CreateActor<>(modelClass, listener, mapper, repository));
    }
    public void enableUpdate(UpdateListener<T> listener){
        actors.add(new UpdateActor<>(modelClass, listener, mapper, repository));
    }
    public void enableDelete(DeleteListener<T> listener){
        actors.add(new DeleteActor<>(modelClass, listener, mapper, repository));
    }
    public void enableRead(ReadListener<T> listener){
        actors.add(new ReadActor<>(modelClass, listener, mapper, repository));
    }
    public void enableReadAll(ReadAllListener<T> listener){
        actors.add(new ReadAllActor<>(modelClass, listener, mapper, repository));
    }

}
