package it.sijmen.movienotifier.jobs;

import org.quartz.impl.calendar.BaseCalendar;

import java.time.*;

/**
 * Created by Sijmen on 10-4-2017.
 */
public class DayNightCalendar extends BaseCalendar {

    private final LocalTime from;
    private final LocalTime to;

    public DayNightCalendar(LocalTime from, LocalTime to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isTimeIncluded(long timeStamp) {
        if (!super.isTimeIncluded(timeStamp)) {
            return false;
        }

        LocalTime time = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalTime();

        if(from.isBefore(to))
            return time.isAfter(from) && time.isBefore(to);
        else
            return time.isAfter(from) || time.isBefore(to);
    }

}
