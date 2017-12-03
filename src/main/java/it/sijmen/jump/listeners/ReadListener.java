package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface ReadListener<T> extends ActorListener<T> {

    default void checkReadRequest(JumpRequest request) {}

    default boolean allowRead(JumpRequest request, T result) {return true; }

    default T beforeReadResult(JumpRequest request, T result){ return result; }
}
