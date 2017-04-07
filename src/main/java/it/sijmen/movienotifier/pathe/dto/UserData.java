package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class UserData implements Serializable {
    private static final long serialVersionUID = -5677634552316601176L;
    @JsonProperty
    private ArrayList<Long> cinemas;
    @JsonProperty
    private ArrayList<Long> favorites;
    @JsonProperty
    private ArrayList<Long> likes;
    @JsonProperty
    private long mostFavoriteCinema;
    @JsonProperty
    private Tile mostFavoriteCinemaTile;
    @JsonProperty
    private ArrayList<MovieRating> ratings;
    @JsonProperty
    private ArrayList<Long> tickets;
    @JsonProperty
    private ArrayList<Long> trailersWatched;
    @JsonProperty
    private long userId;
    @JsonProperty
    private ArrayList<Long> watched;

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<Long> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(ArrayList<Long> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Long> getWatched() {
        return this.watched;
    }

    public void setWatched(ArrayList<Long> watched) {
        this.watched = watched;
    }

    public ArrayList<Long> getTrailersWatched() {
        return this.trailersWatched;
    }

    public void setTrailersWatched(ArrayList<Long> trailersWatched) {
        this.trailersWatched = trailersWatched;
    }

    public ArrayList<Long> getLikes() {
        return this.likes;
    }

    public void setLikes(ArrayList<Long> likes) {
        this.likes = likes;
    }

    public ArrayList<Long> getTickets() {
        return this.tickets;
    }

    public void setTickets(ArrayList<Long> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Long> getCinemas() {
        return this.cinemas;
    }

    public void setCinemas(ArrayList<Long> cinemas) {
        this.cinemas = cinemas;
    }

    public long getMostFavoriteCinema() {
        return this.mostFavoriteCinema;
    }

    public void setMostFavoriteCinema(long mostFavoriteCinema) {
        this.mostFavoriteCinema = mostFavoriteCinema;
    }

    public ArrayList<MovieRating> getRatings() {
        return this.ratings;
    }

    public void setRatings(ArrayList<MovieRating> ratings) {
        this.ratings = ratings;
    }

    public int getRating(long movieId) {
        Iterator it = getRatings().iterator();
        while (it.hasNext()) {
            MovieRating r = (MovieRating) it.next();
            if (r.getMovieId() == movieId) {
                return r.getRating();
            }
        }
        return 0;
    }

    public Tile getMostFavoriteCinemaTile() {
        return this.mostFavoriteCinemaTile;
    }

    public void setMostFavoriteCinemaTile(Tile mostFavoriteCinemaTile) {
        this.mostFavoriteCinemaTile = mostFavoriteCinemaTile;
    }
}
