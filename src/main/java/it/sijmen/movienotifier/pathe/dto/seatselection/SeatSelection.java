package it.sijmen.movienotifier.pathe.dto.seatselection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class SeatSelection implements Serializable {

    private static final long serialVersionUID = -3647142512734133527L;

    @JsonProperty
    private ArrayList<Block> blocks;

    @JsonProperty
    private long id;

    @JsonProperty
    private int seatHeight;

    @JsonProperty
    private int seatWidth;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Block> getBlocks() {
        return this.blocks;
    }

    public int getSeatWidth() {
        return this.seatWidth;
    }

    public void setSeatWidth(int seatWidth) {
        this.seatWidth = seatWidth;
    }

    public int getSeatHeight() {
        return this.seatHeight;
    }

    public void setSeatHeight(int seatHeight) {
        this.seatHeight = seatHeight;
    }

}
