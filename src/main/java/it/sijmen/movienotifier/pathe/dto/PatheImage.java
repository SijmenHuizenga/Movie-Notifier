package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PatheImage implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    @JsonProperty
    private String caption;
    @JsonProperty
    private long id;
    @JsonProperty
    private String url;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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
