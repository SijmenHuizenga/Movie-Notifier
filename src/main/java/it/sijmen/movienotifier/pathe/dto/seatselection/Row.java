package it.sijmen.movienotifier.pathe.dto.seatselection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Row implements Serializable {

    private static final long serialVersionUID = 9001137759040255179L;

    @JsonProperty
    private String name;

    @JsonProperty
    private ArrayList<Seat> seats;

    public ArrayList<Seat> getSeats() {
        return this.seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
