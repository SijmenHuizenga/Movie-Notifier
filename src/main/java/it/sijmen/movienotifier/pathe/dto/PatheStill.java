package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PatheStill implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    @JsonProperty
    private String caption;
    @JsonProperty
    private String filename;
    @JsonProperty
    private long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFileame(String filename) {
        this.filename = filename;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int describeContents() {
        return 0;
    }

}
