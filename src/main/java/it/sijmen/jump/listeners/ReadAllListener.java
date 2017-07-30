package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

import java.util.List;

public interface ReadAllListener<T> extends ActorListener<T> {

    void checkReadAllRequest(JumpRequest request);

    boolean allowReadAll(JumpRequest request, List<T> result);
}
