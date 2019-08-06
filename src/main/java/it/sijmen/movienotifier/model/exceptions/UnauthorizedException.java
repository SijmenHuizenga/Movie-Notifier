package it.sijmen.movienotifier.model.exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UnauthorizedException extends RuntimeException {}
