package it.sijmen.movienotifier.pathe.dto.seatselection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    private static final long serialVersionUID = -8914544487423044065L;

    @JsonProperty
    private String category;
    @JsonProperty
    private long id;
    @JsonProperty
    private ArrayList<Row> rows;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Row> getRows() {
        return this.rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }
}
