package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Photo implements Serializable {

    private static final long serialVersionUID = -5296372905068528855L;

    @JsonProperty
    private String photo;

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int describeContents() {
        return 0;
    }
}
