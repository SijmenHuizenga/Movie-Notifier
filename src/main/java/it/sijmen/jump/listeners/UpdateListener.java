package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface UpdateListener<T> extends ActorListener<T> {

    default boolean allowUpdate(JumpRequest apiKey, T originalModel) {return true; }

    default T beforeUpdateValidation(T model) {return model;}

    default T beforeUpdateStore(T model) {return model;}

    default void checkUpdateRequest(JumpRequest request) {}
}
