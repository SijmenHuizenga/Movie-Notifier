package it.sijmen.jump;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.actors.*;
import it.sijmen.jump.listeners.*;
import it.sijmen.movienotifier.api.WatcherController;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import net.jodah.typetools.TypeResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
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
                Class<T> modelClass) {
        this.mapper = mapper;
        this.repository = repository;
        this.modelClass = modelClass;
    }

    public ResponseEntity handle(JumpRequest request) {
        for(Actor actor : actors){
            if(actor.accepts(request))
                return actor.handle(request);
        }
        return ResponseEntity.notFound().build();
    }

    public Jump<T> enableCreate(CreateListener<T> listener){
        actors.add(new CreateActor<>(modelClass, listener, mapper, repository));
        return this;
    }
    public Jump<T> enableUpdate(UpdateListener<T> listener){
        actors.add(new UpdateActor<>(modelClass, listener, mapper, repository));
        return this;
    }
    public Jump<T> enableDelete(DeleteListener<T> listener){
        actors.add(new DeleteActor<>(modelClass, listener, mapper, repository));
        return this;
    }
    public Jump<T> enableRead(ReadListener<T> listener){
        actors.add(new ReadActor<>(modelClass, listener, mapper, repository));
        return this;
    }
    public Jump<T> enableReadAll(ReadAllListener<T> listener){
        actors.add(new ReadAllActor<>(modelClass, listener, mapper, repository));
        return this;
    }

    public Jump<T> enableReadSome(ReadSomeListener<T> listener) {
        actors.add(new ReadSomeActor<>(modelClass, listener, mapper, repository));
        return this;
    }
}
