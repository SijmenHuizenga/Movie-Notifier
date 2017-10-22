package it.sijmen.movienotifier.service.cinemas.pathe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatheShowing {

    @JsonProperty
    private int cinemaId;

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

    @JsonProperty("3d")
    private Integer is3d;

    @JsonProperty
    private Integer nl;

    @JsonProperty
    private Integer imax;

    @JsonProperty
    private Integer ov;

    @JsonProperty
    private Integer hfr;

    @JsonProperty
    private Integer isAtmos;

    @JsonProperty
    private Integer is4k;

    @JsonProperty
    private Integer isLaser;

    @JsonProperty
    private Boolean is4dx;

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

    public Integer getIs3d() {
        return is3d;
    }

    public void setIs3d(Integer is3d) {
        this.is3d = is3d;
    }

    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    public Integer getImax() {
        return imax;
    }

    public void setImax(Integer imax) {
        this.imax = imax;
    }

    public Integer getOv() {
        return ov;
    }

    public void setOv(Integer ov) {
        this.ov = ov;
    }

    public Integer getHfr() {
        return hfr;
    }

    public void setHfr(Integer hfr) {
        this.hfr = hfr;
    }

    public Integer getIsAtmos() {
        return isAtmos;
    }

    public void setIsAtmos(Integer isAtmos) {
        this.isAtmos = isAtmos;
    }

    public Integer getIs4k() {
        return is4k;
    }

    public void setIs4k(Integer is4k) {
        this.is4k = is4k;
    }

    public Integer getIsLaser() {
        return isLaser;
    }

    public void setIsLaser(Integer isLaser) {
        this.isLaser = isLaser;
    }

    public Boolean getIs4dx() {
        return is4dx;
    }

    public void setIs4dx(Boolean is4dx) {
        this.is4dx = is4dx;
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
