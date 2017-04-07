package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty
    private String code;
    @JsonProperty
    private String message;
    @JsonProperty
    private String target;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean shouldShowToUser() {
        return "user".equals(this.target);
    }
}
