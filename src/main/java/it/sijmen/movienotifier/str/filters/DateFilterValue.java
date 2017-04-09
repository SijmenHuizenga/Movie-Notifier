package it.sijmen.movienotifier.str.filters;

import it.sijmen.movienotifier.str.FilterValue;

import java.util.Calendar;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class DateFilterValue extends FilterValue{

    private Calendar after, before;

    public DateFilterValue(Calendar after, Calendar before) {
        this.after = after;
        this.before = before;
    }

    public Calendar getAfter() {
        return after;
    }

    public Calendar getBefore() {
        return before;
    }

}
