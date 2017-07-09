package it.sijmen.movienotifier.model.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BadRequestException extends RuntimeException {

    private List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }

    public BadRequestException(String error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public BadRequestException() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error){
        this.errors.add(error);
    }

    @Override
    public String getMessage() {
        return String.join("\n", errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
