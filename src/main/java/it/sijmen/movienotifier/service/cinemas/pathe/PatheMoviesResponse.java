package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class PatheMoviesResponse {

    @Id
    private int movieid;

    @JsonProperty("schedules")
    private List<PatheShowing> showings;

    public PatheMoviesResponse(int movieid){
        this.movieid = movieid;
        this.showings = new ArrayList<>();
    }

    public PatheMoviesResponse(){

    }

    public List<PatheShowing> getShowings() {
        return showings;
    }

    public void setShowings(List<PatheShowing> showings) {
        this.showings = showings;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatheMoviesResponse that = (PatheMoviesResponse) o;

        if (getMovieid() != that.getMovieid()) return false;
        return getShowings() != null ? getShowings().equals(that.getShowings()) : that.getShowings() == null;
    }

    @Override
    public int hashCode() {
        int result = getMovieid();
        result = 31 * result + (getShowings() != null ? getShowings().hashCode() : 0);
        return result;
    }

    public List<Long> getShowingsids() {
        ArrayList<Long> out = new ArrayList<>();
        for(PatheShowing s : showings)
            out.add(s.getId());
        return out;
    }
}
