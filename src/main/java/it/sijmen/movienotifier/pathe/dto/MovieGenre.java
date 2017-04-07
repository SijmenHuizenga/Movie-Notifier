package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MovieGenre implements Serializable {

    private static final long serialVersionUID = 3933798264215360586L;

    @JsonProperty
    private long id;
    @JsonProperty
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int describeContents() {
        return 0;
    }
}
