package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PatheShowing {

    @JsonProperty
    private int cinemaId;

    @JsonProperty
    private int is3d;

    @JsonProperty
    private int nl;

    @JsonProperty
    private int imax;

    @JsonProperty
    private int ov;

    @JsonProperty
    private int hfr;

    @JsonProperty
    private int isAtmos;

    @JsonProperty
    private int is4k;

    @JsonProperty
    private int isLaser;

    @JsonProperty
    private long movieId;

    @JsonProperty
    private long id;

    @JsonDeserialize(using=UnixTimestampDeserializer.class)
    @JsonProperty
    private long start;

    @JsonDeserialize(using=UnixTimestampDeserializer.class)
    @JsonProperty
    private long end;

    public long getEnd() {
        return this.end == -1 ? getFakeEnd() : this.end;
    }

    private long getFakeEnd() {
        //3 hours in millis
        return this.start + 10_800_000;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getIs3d() {
        return is3d;
    }

    public void setIs3d(int is3d) {
        this.is3d = is3d;
    }

    public int getNl() {
        return nl;
    }

    public void setNl(int nl) {
        this.nl = nl;
    }

    public int getImax() {
        return imax;
    }

    public void setImax(int imax) {
        this.imax = imax;
    }

    public int getOv() {
        return ov;
    }

    public void setOv(int ov) {
        this.ov = ov;
    }

    public int getHfr() {
        return hfr;
    }

    public void setHfr(int hfr) {
        this.hfr = hfr;
    }

    public int getIsAtmos() {
        return isAtmos;
    }

    public void setIsAtmos(int isAtmos) {
        this.isAtmos = isAtmos;
    }

    public int getIs4k() {
        return is4k;
    }

    public void setIs4k(int is4k) {
        this.is4k = is4k;
    }

    public int getIsLaser() {
        return isLaser;
    }

    public void setIsLaser(int isLaser) {
        this.isLaser = isLaser;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatheShowing that = (PatheShowing) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
