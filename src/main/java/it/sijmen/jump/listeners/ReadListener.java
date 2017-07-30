package it.sijmen.jump.listeners;

public interface ReadListener<T> extends ActorListener<T> {

    boolean allowRead(String apiKey, T result);

}
