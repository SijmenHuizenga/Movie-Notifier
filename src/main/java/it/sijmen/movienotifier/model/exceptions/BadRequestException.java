package it.sijmen.movienotifier.model.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class BadRequestException extends RuntimeException {

    @JsonProperty
    private final List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }

    public BadRequestException(String error) {
        this.errors = Collections.singletonList(error);
    }

    @Override
    public String getMessage() {
        return String.join("\n", errors);
    }
}
