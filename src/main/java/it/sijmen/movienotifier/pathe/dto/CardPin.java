package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CardPin implements Serializable {

    private static final long serialVersionUID = -171633478698658560L;

    @JsonProperty
    private String pin;

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int describeContents() {
        return 0;
    }

}
