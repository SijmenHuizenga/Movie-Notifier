package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Extra implements Serializable {
    private static final long serialVersionUID = 7275456039882298464L;
    @JsonProperty
    private boolean added;
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean optional;
    @JsonProperty
    private int price;
    @JsonProperty
    private long ticketId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public boolean isAdded() {
        return this.added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
