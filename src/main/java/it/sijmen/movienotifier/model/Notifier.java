package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public abstract class Notifier {

    public abstract void notify(User recipient, String message) throws IOException;

    @JsonProperty(value="key", access = JsonProperty.Access.READ_ONLY)
    public abstract String getId();

    @JsonProperty(value="name", access = JsonProperty.Access.READ_ONLY)
    public abstract String getName();

    @JsonProperty(value="description", access = JsonProperty.Access.READ_ONLY)
    public abstract String getDescription();

}
