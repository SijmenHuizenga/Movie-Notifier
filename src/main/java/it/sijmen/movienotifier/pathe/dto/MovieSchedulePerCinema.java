package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class MovieSchedulePerCinema {

    private static final long serialVersionUID = -4593722361788767522L;

    @JsonProperty("cinemas")
    private ArrayList<Cinema> mCinemaArray;

    @JsonProperty("cities")
    private ArrayList<Cinema> mCities;

    @JsonProperty("schedulesPerCinema")
    private Map<Long, ArrayList<PatheSchedule>> mSchedulePerCinemaList;

    @JsonProperty("schedules")
    private ArrayList<PatheSchedule> schedules;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Cinema> getmCinemaArray() {
        return mCinemaArray;
    }

    public void setmCinemaArray(ArrayList<Cinema> mCinemaArray) {
        this.mCinemaArray = mCinemaArray;
    }

    public ArrayList<Cinema> getmCities() {
        return mCities;
    }

    public void setmCities(ArrayList<Cinema> mCities) {
        this.mCities = mCities;
    }

    public Map<Long, ArrayList<PatheSchedule>> getmSchedulePerCinemaList() {
        return mSchedulePerCinemaList;
    }

    public void setmSchedulePerCinemaList(Map<Long, ArrayList<PatheSchedule>> mSchedulePerCinemaList) {
        this.mSchedulePerCinemaList = mSchedulePerCinemaList;
    }

    public ArrayList<PatheSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<PatheSchedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "MovieSchedulePerCinema{" +
                "mCinemaArray=" + mCinemaArray +
                ", mCities=" + mCities +
                ", mSchedulePerCinemaList=" + mSchedulePerCinemaList +
                ", schedules=" + schedules +
                '}';
    }
}
