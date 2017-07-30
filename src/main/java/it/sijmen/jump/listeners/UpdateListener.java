package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface UpdateListener<T> extends ActorListener<T> {

    boolean allowUpdate(JumpRequest apiKey, T originalModel);

    T beforeUpdateValidation(T model);

    T beforeUpdateStore(T model);

    void checkUpdateRequest(JumpRequest request);
}
