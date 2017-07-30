package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface DeleteListener<T> extends ActorListener<T> {

    void checkDeleteRequest(JumpRequest request);

    boolean allowDelete(JumpRequest apiKey, T toDelete);
}
