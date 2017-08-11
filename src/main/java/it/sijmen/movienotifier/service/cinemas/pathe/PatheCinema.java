package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatheCinema {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PatheCinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
