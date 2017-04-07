package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PatheMoviesOverview implements Serializable {
    private static final long serialVersionUID = 1;
    @JsonProperty
    private PatheMovieList comingsoon;
    @JsonProperty
    private PatheMovieList nowplaying;
    @JsonProperty
    private PatheMovieList top10;
    @JsonProperty
    private TrailerList trailers;
    @JsonProperty
    private String type;
    @JsonProperty
    private PatheMovieList vod;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PatheMovieList getTop10() {
        return this.top10;
    }

    public void setTop10(PatheMovieList top10) {
        this.top10 = top10;
    }

    public PatheMovieList getNowplaying() {
        return this.nowplaying;
    }

    public void setNowplaying(PatheMovieList nowplaying) {
        this.nowplaying = nowplaying;
    }

    public PatheMovieList getComingsoon() {
        return this.comingsoon;
    }

    public void setComingsoon(PatheMovieList comingsoon) {
        this.comingsoon = comingsoon;
    }

    public TrailerList getTrailers() {
        return this.trailers;
    }

    public void setTrailers(TrailerList trailers) {
        this.trailers = trailers;
    }

    public PatheMovieList getVod() {
        return this.vod;
    }

    public void setVod(PatheMovieList vod) {
        this.vod = vod;
    }
}
