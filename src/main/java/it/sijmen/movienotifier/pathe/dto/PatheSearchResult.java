package it.sijmen.movienotifier.pathe.dto;

import java.util.ArrayList;

public class PatheSearchResult {
    private ArrayList<Movie> mMovieList;

    public PatheSearchResult() {
        this.mMovieList = new ArrayList();
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.mMovieList = movieList;
    }

    public ArrayList<Movie> getMovieList() {
        return this.mMovieList;
    }
}
