package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

import java.util.List;

public class JumpListenerAdapter<T> implements CreateListener<T>, DeleteListener<T>, ReadAllListener<T>, ReadListener<T>, UpdateListener<T> {

    @Override
    public T beforeCreateStore(T model) {
        return model;
    }

    @Override
    public T beforeCreateValidation(T model) {
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
    public void checkUpdateRequest(JumpRequest request) {}

    @Override
    public void checkCreateRequest(JumpRequest request) {}

    @Override
    public boolean allowCreate(JumpRequest request, T model) {
        return false;
    }

    @Override
    public void checkReadRequest(JumpRequest request) {}

    @Override
    public boolean allowRead(JumpRequest request, T result) {
        return false;
    }

    @Override
    public void checkDeleteRequest(JumpRequest request) {

    }

    @Override
    public boolean allowDelete(JumpRequest request, T toDelete) {
        return false;
    }

    @Override
    public boolean allowUpdate(JumpRequest request, T originalModel) {
        return false;
    }

    @Override
    public void checkReadAllRequest(JumpRequest request) {}

    @Override
    public boolean allowReadAll(JumpRequest request, List<T> result) {
        return false;
    }

}
