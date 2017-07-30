package it.sijmen.jump.listeners;

import java.util.List;

public interface ReadAllListener<T> extends ActorListener<T> {

    boolean allowReadAll(String apiKey, List<T> result);

}
