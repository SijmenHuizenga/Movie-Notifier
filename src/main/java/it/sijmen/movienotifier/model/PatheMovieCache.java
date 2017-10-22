package it.sijmen.movienotifier.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Document
public class PatheMovieCache {

    @Id
    private int movieid;

    private List<Long> showingids;


    public PatheMovieCache() { }

    public PatheMovieCache(int movieid) {
        this.movieid = movieid;
    }

    public PatheMovieCache(int movieid, List<Long> showingsids) {
        this.movieid = movieid;
        this.showingids = showingsids;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public List<Long> getShowingids() {
        if(showingids == null)
            return Collections.emptyList();
        return showingids;
    }

    public void setShowingids(List<Long> showingids) {
        this.showingids = showingids;
    }

    @Override
    public String toString() {
        return "PatheMovieCache{" +
                "movieid=" + movieid +
                ", showingids=" + showingids +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatheMovieCache that = (PatheMovieCache) o;

        return getMovieid() == that.getMovieid();
    }

    @Override
    public int hashCode() {
        return getMovieid();
    }
}
