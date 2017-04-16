package it.sijmen.movienotifier;

/**
 * Created by Sijmen on 15-4-2017.
 */
public interface Validateble {

    boolean isValid();

    default boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    default boolean isValid(final Object o){
        if(o == null)
            return false;
        if(o instanceof Validateble)
            return ((Validateble)o).isValid();
        return true;
    }

}
