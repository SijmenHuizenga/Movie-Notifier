package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.List;

public class QuickSearchItem {
    @JsonProperty
    private List<PathePerson> cast;
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Calendar releaseDate;
    private ResultType resultType;
    @JsonProperty
    private String thumb;
    @JsonProperty
    private String versionString;

    public enum ResultType {
        MOVIE,
        CAST,
        FAQ
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Calendar getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public List<PathePerson> getCast() {
        return this.cast;
    }

    public void setCast(List<PathePerson> cast) {
        this.cast = cast;
    }

    public String getVersionString() {
        return this.versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }
}
