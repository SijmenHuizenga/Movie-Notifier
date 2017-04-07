package it.sijmen.movienotifier.pathe.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MovieSchedule implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    public final String TAG;
    private long id;
    private String mAge;
    private ArrayList<PatheSchedule> mScheduleList;
    private String name;
    private PatheKijkwijzer patheKijkwijzer;
    private int rating;
    private Calendar releaseDate;
    private String thumb;
    private long visitors;

    public class ScheduleComparator implements Comparator<PatheSchedule> {
        public int compare(PatheSchedule o1, PatheSchedule o2) {
            return o1.getStartOfShow().compareTo(o2.getStartOfShow());
        }
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getVisitors() {
        return this.visitors;
    }

    public void setVisitors(long visitors) {
        this.visitors = visitors;
    }

    public MovieSchedule() {
        this.TAG = getClass().getSimpleName();
        this.mScheduleList = new ArrayList<>();
    }

    public long getMovieId() {
        return this.id;
    }

    public void setMovieId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpAge() {
        return this.mAge;
    }

    public PatheKijkwijzer getMovieKijkwijzer() {
        return this.patheKijkwijzer;
    }

    public void setMovieKijkwijzer(PatheKijkwijzer patheKijkwijzer) {
        this.patheKijkwijzer = patheKijkwijzer;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public ArrayList<PatheSchedule> getScheduleList() {
        return this.mScheduleList;
    }

    public void setScheduleList(ArrayList<PatheSchedule> scheduleList) {
        Collections.sort(scheduleList, new ScheduleComparator());
        this.mScheduleList = scheduleList;
    }

}
