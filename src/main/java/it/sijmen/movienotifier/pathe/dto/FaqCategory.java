package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FaqCategory {

    @JsonProperty
    private List<FaqForum> forums;
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FaqForum> getForums() {
        return this.forums;
    }

    public void setForums(List<FaqForum> forums) {
        this.forums = forums;
    }
}
