package it.sijmen.movienotifier.str.filters;

import it.sijmen.movienotifier.str.FilterValue;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class IntegerFilter extends FilterValue<Integer> {

    private int value;

    public IntegerFilter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean accepts(Integer value) {
        return this.value == value;
    }
}
