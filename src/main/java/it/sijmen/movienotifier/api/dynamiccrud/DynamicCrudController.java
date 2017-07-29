package it.sijmen.movienotifier.api.dynamiccrud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class DynamicCrudController<T extends Model> {

    private final DynamicCrudControllerListener<T> listener;
    private final ObjectMapper mapper;
    private final MongoRepository<T, String> repository;

    @Inject
    public DynamicCrudController(@NotNull ObjectMapper mapper,
                                 @NotNull MongoRepository<T, String> repository,
                                 @NotNull DynamicCrudControllerListener<T> listener) {
        this.mapper = mapper;
        this.repository = repository;
        this.listener = listener;
    }

    public HttpEntity<?> handle(
            Class<? extends T> model,
            HttpMethod requestMethod,
            Map<String, String> requestHeaders,
            String urldata,
            String body) {

        if(requestMethod == HttpMethod.GET){
            if(urldata != null)
                return read(requestHeaders, urldata);
            return readAll(requestHeaders);
        }

        if(requestMethod == HttpMethod.PUT && urldata == null)
            return create(model, requestHeaders, body);
        if(requestMethod == HttpMethod.POST && urldata != null)
            return update(model, requestHeaders, urldata, body);
        if(requestMethod == HttpMethod.DELETE && urldata != null)
            return delete(requestHeaders, urldata);
        throw new BadRequestException();
    }

    private ResponseEntity<T> create(Class<? extends T> modelClass, Map<String, String> requestHeaders, String body){
        T model = readModelFromBody(modelClass, body);

        if(!listener.allowCreation(getApiKey(requestHeaders), model))
            throw new UnauthorizedException();

        model = listener.beforeCreationValidation(model);
        model.validate();

        model = listener.beforeCreationStore(model);
        repository.save(model);

        //todo replace with .created(location) ???
        return ResponseEntity.ok(model);
    }

    private HttpEntity<?> read(Map<String, String> requestHeaders, @NotNull String modelId){
        T result = repository.findOne(modelId);
        if(!listener.allowRead(getApiKey(requestHeaders), result))
            throw new UnauthorizedException();
        return ResponseEntity.ok(result);
    }

    private HttpEntity<?> readAll(Map<String, String> requestHeaders){
        List<T> result = repository.findAll();
        if(!listener.allowReadAll(getApiKey(requestHeaders), result))
            throw new UnauthorizedException();
        return ResponseEntity.ok(result);
    }

    private HttpEntity<?> update(Class<? extends T> modelClass, Map<String, String> requestHeaders, String modelId, String body){
        T model = repository.findOne(modelId);
        T updatingData = readModelFromBody(modelClass, body);

        if(!listener.allowUpdate(getApiKey(requestHeaders), model))
            throw new UnauthorizedException();

        final T finalModel = model;
        Arrays.stream(updatingData.getClass().getFields())
                .filter(field -> field.isAnnotationPresent(JsonProperty.class)
                        && field.getAnnotation(JsonProperty.class).access() != READ_ONLY)
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(updatingData) != null;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).forEach((Field field) -> {
                    try {
                        field.set(finalModel, field.get(updatingData));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        model = listener.beforeUpdateValidation(finalModel);
        model.validate();

        model = listener.beforeUpdateStore(model);
        repository.save(model);

        return ResponseEntity.ok(model);
    }

    private ResponseEntity delete(Map<String, String> requestHeaders, @NotNull String modelId){
        T toDelete = repository.findOne(modelId);
        if(!listener.allowDelete(getApiKey(requestHeaders), toDelete))
            throw new UnauthorizedException();
        repository.delete(toDelete);
        return ResponseEntity.ok().build();
    }

    private String getApiKey(Map<String, String> requestHeaders) {
        return requestHeaders == null ? null : requestHeaders.getOrDefault("APIKEY", null);
    }

    private T readModelFromBody(Class<? extends T> modelClass, String body) {
        if(body == null)
            throw new BadRequestException("No details provided");
        try {
            return mapper.readValue(body, modelClass);
        } catch (IOException e) {
            throw new HttpMessageNotReadableException(e.getMessage());
        }
    }
}
