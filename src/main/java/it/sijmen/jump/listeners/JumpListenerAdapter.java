package it.sijmen.jump.listeners;

public interface JumpListenerAdapter<T> extends CreateListener<T>, DeleteListener<T>, ReadAllListener<T>, ReadListener<T>, UpdateListener<T> {

}
