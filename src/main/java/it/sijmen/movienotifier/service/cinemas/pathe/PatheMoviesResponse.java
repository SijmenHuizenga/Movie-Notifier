package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class PatheMoviesResponse {

    @Id
    private int movieid;

    @JsonProperty("schedules")
    private ArrayList<PatheShowing> showings;

    @JsonProperty
    private ArrayList<PatheCinema> cinemas;

    @JsonProperty
    private ArrayList<PatheCity> cities;

    public ArrayList<PatheShowing> getShowings() {
        return showings;
    }

    public void setShowings(ArrayList<PatheShowing> showings) {
        this.showings = showings;
    }

    public ArrayList<PatheCinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(ArrayList<PatheCinema> cinemas) {
        this.cinemas = cinemas;
    }

    public ArrayList<PatheCity> getCities() {
        return cities;
    }

    public void setCities(ArrayList<PatheCity> cities) {
        this.cities = cities;
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
        if (getShowings() != null ? !getShowings().equals(that.getShowings()) : that.getShowings() != null)
            return false;
        if (getCinemas() != null ? !getCinemas().equals(that.getCinemas()) : that.getCinemas() != null) return false;
        return getCities() != null ? getCities().equals(that.getCities()) : that.getCities() == null;
    }

    @Override
    public int hashCode() {
        int result = getMovieid();
        result = 31 * result + (getShowings() != null ? getShowings().hashCode() : 0);
        result = 31 * result + (getCinemas() != null ? getCinemas().hashCode() : 0);
        result = 31 * result + (getCities() != null ? getCities().hashCode() : 0);
        return result;
    }
}
