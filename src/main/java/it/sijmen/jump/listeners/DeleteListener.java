package it.sijmen.jump.listeners;

public interface DeleteListener<T> extends ActorListener<T> {

    boolean allowDelete(String apiKey, T toDelete);

}
