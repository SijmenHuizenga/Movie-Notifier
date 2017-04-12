package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class MovieSchedulePerCinema {

    private static final long serialVersionUID = -4593722361788767522L;

    @JsonProperty("cinemas")
    public ArrayList<Cinema> cinemas;

    @JsonProperty("cities")
    public ArrayList<Cinema> cities;

    @JsonProperty("schedulesPerCinema")
    public Map<Long, ArrayList<PatheSchedule>> schedulesPerCinema;

    @JsonProperty("schedules")
    private ArrayList<PatheSchedule> schedules;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(ArrayList<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public ArrayList<Cinema> getCities() {
        return cities;
    }

    public void setCities(ArrayList<Cinema> cities) {
        this.cities = cities;
    }

    public Map<Long, ArrayList<PatheSchedule>> getSchedulesPerCinema() {
        return schedulesPerCinema;
    }

    public void setSchedulesPerCinema(Map<Long, ArrayList<PatheSchedule>> schedulesPerCinema) {
        this.schedulesPerCinema = schedulesPerCinema;
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
                "cinemas=" + cinemas +
                ", cities=" + cities +
                ", schedulesPerCinema=" + schedulesPerCinema +
                ", schedules=" + schedules +
                '}';
    }
}
