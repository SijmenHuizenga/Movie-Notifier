package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Experience {

    @JsonProperty
    private String description;
    @JsonProperty
    private long id;
    @JsonProperty
    private String image;
    @JsonProperty
    private String name;
    @JsonProperty
    private int seqno;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSeqno() {
        return this.seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }
}
