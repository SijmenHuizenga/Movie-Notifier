package it.sijmen.jump.listeners;

import java.util.List;

public class JumpListenerAdapter<T> implements CreateListener<T>, DeleteListener<T>, ReadAllListener<T>, ReadListener<T>, UpdateListener<T> {

    @Override
    public T beforeCreationStore(T model) {
        return model;
    }

    @Override
    public T beforeCreationValidation(T model) {
        return model;
    }

    @Override
    public T beforeUpdateValidation(T model) {
        return model;
    }

    @Override
    public T beforeUpdateStore(T model) {
        return model;
    }

    @Override
    public boolean allowCreation(String apikey, T model) {
        return false;
    }

    @Override
    public boolean allowRead(String apiKey, T result) {
        return false;
    }

    @Override
    public boolean allowDelete(String apiKey, T toDelete) {
        return false;
    }

    @Override
    public boolean allowUpdate(String apiKey, T originalModel) {
        return false;
    }

    @Override
    public boolean allowReadAll(String apiKey, List<T> result) {
        return false;
    }

}
