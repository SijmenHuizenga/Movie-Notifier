package it.sijmen.jump.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.UpdateListener;
import it.sijmen.movienotifier.model.Model;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        listener.checkUpdateRequest(request);

        final T model = repository.findOne(request.getUrldata());
        final T updatingData = readModelFromBody(modelClass, request.getBody());

        if(!listener.allowUpdate(request, model))
            throw new UnauthorizedException();

        return ResponseEntity.ok(
                updateModel(model, updatingData)
        );
    }

    private T updateModel(T model, T updatingData){
        getAllFieldsIncludingSuperclass(updatingData.getClass())
                .stream()
                .filter(this::hasWritableJsonProperty)
                .filter(field -> this.isNotNullOnObject(field, updatingData))
                .forEach((Field field) -> copyField(field, updatingData, model));

        T model2 = listener.beforeUpdateValidation(model);
        model2.validate();

        model2 = listener.beforeUpdateStore(model2);
        repository.save(model2);
        return model2;
    }

    private List<Field> getAllFieldsIncludingSuperclass(Class claz){
        List<Field> out = new ArrayList<>();
        do{
            Collections.addAll(out, claz.getDeclaredFields());
            claz = claz.getSuperclass();
        }while(claz != null && !claz.equals(Object.class));
        return out;
    }

    private boolean hasWritableJsonProperty(Field field){
        return field.isAnnotationPresent(JsonProperty.class)
                && field.getAnnotation(JsonProperty.class).access() != READ_ONLY;
    }

    private boolean isNotNullOnObject(Field field, Object object){
        try {
            field.setAccessible(true);
            Object o = field.get(object);
            return o != null && !isEmptyArray(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isEmptyArray(@NotNull Object object){
        return object instanceof List && ((List) object).isEmpty();
    }

    private void copyField(Field field, Object source, Object target){
        try {
            field.set(target, field.get(source));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
