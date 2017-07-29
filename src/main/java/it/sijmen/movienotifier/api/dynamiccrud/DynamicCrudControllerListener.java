package it.sijmen.movienotifier.api.dynamiccrud;

import it.sijmen.movienotifier.model.Model;

import java.util.List;

public interface DynamicCrudControllerListener<T extends Model> {

    T beforeCreationStore(T model);

    T beforeCreationValidation(T model);

    boolean allowCreation(String apikey, T model);

    boolean allowRead(String apiKey, T result);

    boolean allowReadAll(String apiKey, List<T> result);

    boolean allowDelete(String apiKey, T toDelete);

    boolean allowUpdate(String apiKey, T originalModel);

    T beforeUpdateValidation(T model);

    T beforeUpdateStore(T model);

}
