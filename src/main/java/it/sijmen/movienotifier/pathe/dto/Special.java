package it.sijmen.movienotifier.pathe.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Special implements Serializable {
    private static final long serialVersionUID = 7270346039882298464L;
    @JsonProperty
    private String body;
    @JsonProperty
    private String headerImage;
    @JsonProperty
    private long id;
    @JsonProperty
    private String intro;
    @JsonProperty
    private ArrayList<Movie> movies;
    @JsonProperty
    private String name;
    @JsonProperty
    private String showType;
    @JsonProperty("class")
    private String specialClass;
    @JsonProperty
    private String tooltipImage;
    @JsonProperty
    private String type;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public boolean isSpecial() {
        return "special".equals(this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHeaderImage() {
        return this.headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getTooltipImage() {
        return this.tooltipImage;
    }

    public void setTooltipImage(String tooltipImage) {
        this.tooltipImage = tooltipImage;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Movie getFirstMovieWithTrailerAndSchedule() {
        if (this.movies == null) {
            return null;
        }
        Iterator it = this.movies.iterator();
        while (it.hasNext()) {
            Movie movie = (Movie) it.next();
            ArrayList<Trailer> list = movie.getTrailerList();
            if (list != null && list.size() >= 1) {
                ArrayList<PatheSchedule> list2 = movie.getScheduleList();
                if (list2 == null) {
                    continue;
                } else if (list2.size() >= 1) {
                    return movie;
                }
            }
        }
        return null;
    }

}
