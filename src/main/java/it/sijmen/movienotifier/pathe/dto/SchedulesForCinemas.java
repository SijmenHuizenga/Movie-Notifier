package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

public class SchedulesForCinemas implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;
    public final String TAG;

    @JsonProperty("cinemas")
    List<Cinema> mCinemas;

    @JsonProperty("displayDate")
    private Calendar mDisplayDate;

    @JsonProperty("firstSchedule")
    private Calendar mFirstSchedule;

    @JsonProperty("lastSchedule")
    private Calendar mLastSchedule;

    @JsonProperty("schedules")
    private List<PatheSchedule> mScheduleList;

    @JsonProperty("movies")
    private ArrayList<Movie> movieList;

    public SchedulesForCinemas() {
        this.TAG = getClass().getSimpleName();
        this.movieList = new ArrayList<>();
        this.mScheduleList = new ArrayList<>();
        this.mFirstSchedule = Calendar.getInstance();
        this.mLastSchedule = Calendar.getInstance();
        this.mDisplayDate = Calendar.getInstance();
    }

    public void setFirstSchedule(Calendar earliestSchedule) {
        this.mFirstSchedule = earliestSchedule;
    }

    public void setDisplayDate(Calendar displayDate) {
        this.mDisplayDate = displayDate;
    }

    public void setLastSchedule(Calendar lastSchedule) {
        this.mLastSchedule = lastSchedule;
    }

    public Calendar getFirstSchedule() {
        return this.mFirstSchedule;
    }

    public Calendar getLastSchedule() {
        return this.mLastSchedule;
    }

    public Calendar getDisplayDate() {
        return this.mDisplayDate;
    }

    public int describeContents() {
        return 0;
    }

    public List<PatheSchedule> getScheduleList() {
        return this.mScheduleList;
    }

    public void setScheduleList(List<PatheSchedule> scheduleList) {
        this.mScheduleList = scheduleList;
    }

    public ArrayList<Movie> getMovieList() {
        return this.movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    private boolean hasKijkwijzer(int movieKijkwijzerMask, int filterKijkwijzerMask) {
        return (filterKijkwijzerMask | movieKijkwijzerMask) == filterKijkwijzerMask;
    }

    private boolean hasAge(int movieAge, int filterAge) {
        return movieAge <= filterAge;
    }

    public List<Cinema> getCinemas() {
        return this.mCinemas;
    }

    public void setCinemas(List<Cinema> mCinemas) {
        this.mCinemas = mCinemas;
    }

}
