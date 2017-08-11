package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

import java.util.List;

public interface ReadSomeListener<T> extends ActorListener<T> {

    default void checkReadSomeRequest(JumpRequest request) {}

    default List<T> getReadSomeResult(JumpRequest request) {return null;}
}