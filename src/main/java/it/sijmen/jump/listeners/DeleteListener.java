package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface DeleteListener<T> extends ActorListener<T> {

    default void checkDeleteRequest(JumpRequest request){  }

    default boolean allowDelete(JumpRequest apiKey, T toDelete){ return true; }

    default void postDelete(JumpRequest apiRequest, T deleted){}
}
