package it.sijmen.jump.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.ActorListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.util.Map;

public abstract class Actor<T extends Model, L extends ActorListener> {

    protected Class<? extends T> modelClass;
    protected L listener;
    protected final ObjectMapper mapper;
    protected final MongoRepository<T, String> repository;

    public Actor(Class<? extends T> modelClass, L listener, ObjectMapper mapper, MongoRepository<T, String> repository){
        this.modelClass = modelClass;
        this.listener = listener;
        this.mapper = mapper;
        this.repository = repository;
    }

    public abstract boolean accepts(JumpRequest request);

    public abstract ResponseEntity handle(JumpRequest request);

    protected String getApiKey(Map<String, String> requestHeaders) {
        return requestHeaders == null ? null : requestHeaders.getOrDefault("APIKEY", null);
    }

    protected T readModelFromBody(Class<? extends T> modelClass, String body) {
        if(body == null)
            throw new BadRequestException("No details provided");
        try {
            return mapper.readValue(body, modelClass);
        } catch (IOException e) {
            throw new HttpMessageNotReadableException(e.getMessage());
        }
    }

}
