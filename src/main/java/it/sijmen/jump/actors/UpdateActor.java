package it.sijmen.jump.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.UpdateListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class UpdateActor<T extends Model> extends Actor<T, UpdateListener<T>> {

    public UpdateActor(Class<? extends T> model, UpdateListener<T> listener, ObjectMapper mapper, MongoRepository<T, String> repository) {
        super(model, listener, mapper, repository);
    }

    @Override
    public boolean accepts(JumpRequest request) {
        return request.getMethod() == HttpMethod.POST && request.getUrldata() != null;
    }

    @Override
    public ResponseEntity handle(JumpRequest request) {
        T model = repository.findOne(request.getUrldata());
        T updatingData = readModelFromBody(modelClass, request.getBody());

        if(!listener.allowUpdate(getApiKey(request.getHeaders()), model))
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
}
