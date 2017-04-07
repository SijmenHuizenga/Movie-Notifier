package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MovieRating implements Serializable {
    private static final long serialVersionUID = 1879776172132210727L;
    @JsonProperty
    private long movieId;
    @JsonProperty
    private int rating;

    public long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
