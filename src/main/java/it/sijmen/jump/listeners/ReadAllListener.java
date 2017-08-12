package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

import java.util.List;

public interface ReadAllListener<T> extends ActorListener<T> {

    default void checkReadAllRequest(JumpRequest request) {};

    default boolean allowReadAll(JumpRequest request, List<T> result) {return true; }
}
