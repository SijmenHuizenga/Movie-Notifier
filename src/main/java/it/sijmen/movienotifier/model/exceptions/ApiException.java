package it.sijmen.movienotifier.model.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
