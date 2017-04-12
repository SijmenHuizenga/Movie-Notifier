package it.sijmen.movienotifier.str;

/**
 * Created by Sijmen on 9-4-2017.
 */
public abstract class FilterValue<T> {

    public abstract boolean accepts(T value);

}
