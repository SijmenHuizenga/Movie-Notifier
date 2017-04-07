package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FaqForum {

    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private List<FaqTopic> topics;

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

    public List<FaqTopic> getTopics() {
        return this.topics;
    }

    public void setTopics(List<FaqTopic> topics) {
        this.topics = topics;
    }
}
