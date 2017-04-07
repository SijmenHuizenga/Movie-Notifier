package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TicketTypeAndAmount implements Serializable {
    private static final long serialVersionUID = 5578559313149870142L;
    @JsonProperty
    private int number;
    @JsonProperty
    private long type;

    public TicketTypeAndAmount(long type, int number) {
        this.type = type;
        this.number = number;
    }

    public long getType() {
        return this.type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
