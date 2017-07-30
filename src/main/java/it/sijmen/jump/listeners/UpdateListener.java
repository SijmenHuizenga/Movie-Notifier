package it.sijmen.jump.listeners;

public interface UpdateListener<T> extends ActorListener<T> {

    boolean allowUpdate(String apiKey, T originalModel);

    T beforeUpdateValidation(T model);

    T beforeUpdateStore(T model);

}
