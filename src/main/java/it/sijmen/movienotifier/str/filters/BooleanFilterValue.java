package it.sijmen.movienotifier.str.filters;

import it.sijmen.movienotifier.str.FilterValue;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class BooleanFilterValue extends FilterValue {

    private boolean value;

    public BooleanFilterValue(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

}
