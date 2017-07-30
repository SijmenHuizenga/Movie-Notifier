package it.sijmen.jump.listeners;

public interface CreateListener<T> extends ActorListener<T> {

    T beforeCreationStore(T model);

    T beforeCreationValidation(T model);

    boolean allowCreation(String apikey, T model);

}
