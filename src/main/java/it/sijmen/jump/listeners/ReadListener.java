package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface ReadListener<T> extends ActorListener<T> {

    void checkReadRequest(JumpRequest request);

    boolean allowRead(JumpRequest request, T result);

}
