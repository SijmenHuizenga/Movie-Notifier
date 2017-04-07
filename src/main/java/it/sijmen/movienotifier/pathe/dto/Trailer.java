package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;

public class Trailer implements Serializable {

    private static final long serialVersionUID = -6200816136511004583L;
    public final String TAG;
    @JsonProperty
    private String caption;
    @JsonProperty
    private String filename;
    @JsonProperty
    private long id;
    @JsonProperty
    private long movieId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Calendar releaseDate;
    @JsonProperty
    private String status;
    @JsonProperty
    private String still;
    @JsonProperty
    private String thumb;

    public Trailer() {
        this.TAG = getClass().getSimpleName();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFileame(String filename) {
        setFilename(filename);
    }

    public String getStill() {
        return this.still;
    }

    public void setStill(String still) {
        this.still = still;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int describeContents() {
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }
}
