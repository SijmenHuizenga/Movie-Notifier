package it.sijmen.movienotifier.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class ModelUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUpdater.class);

    public void applyUpdates(Object model, Object updatingData) {
        getAllFieldsIncludingSuperclass(updatingData.getClass())
                .stream()
                .filter(this::hasWritableJsonProperty)
                .filter(field -> this.isNotNullOnObject(field, updatingData))
                .forEach((Field field) -> copyField(field, updatingData, model));
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
            return o != null;
        } catch (IllegalAccessException e) {
            LOGGER.error("No access to field {} while checking {}", field, object, e);
            throw new BadRequestException("Something went wrong.");
        }
    }

    private void copyField(Field field, Object source, Object target){
        try {
            if(field.isAnnotationPresent(RecursiveUpdate.class)) {
                applyUpdates(field.get(target), field.get(source));
                return;
            }
            field.set(target, field.get(source));
        } catch (IllegalAccessException e) {
            LOGGER.error("No access to field {} while copying data from {} to {}", field, source, target, e);
            throw new BadRequestException("Something went wrong.");
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RecursiveUpdate {}

}
