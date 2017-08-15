package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public interface Notifier extends Model {

    void notify(User recipient, String message) throws IOException;

    @JsonProperty(value="key", access = JsonProperty.Access.READ_ONLY)
    String getId();

    @JsonProperty(value="name", access = JsonProperty.Access.READ_ONLY)
    String getName();

    @JsonProperty(value="description", access = JsonProperty.Access.READ_ONLY)
    String getDescription();

}
