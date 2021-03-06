package it.sijmen.movienotifier.service.pathe.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import it.sijmen.movienotifier.model.Cinema;
import it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer;
import it.sijmen.movienotifier.service.pathe.CinemaService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatheShowing implements Comparable<PatheShowing> {

  @JsonProperty private int cinemaId;

  @JsonProperty private long movieId;

  @JsonProperty private long id;

  @JsonDeserialize(using = UnixTimestampDeserializer.class)
  @JsonProperty
  private long start;

  @JsonDeserialize(using = UnixTimestampDeserializer.class)
  @JsonProperty
  private long end;

  @JsonProperty("3d")
  private Integer is3d;

  @JsonProperty private Integer nl;

  @JsonProperty private Integer imax;

  @JsonProperty private Integer ov;

  @JsonProperty private Integer hfr;

  @JsonProperty private Integer isAtmos;

  @JsonProperty private Integer is4k;

  @JsonProperty private Integer isLaser;

  @JsonProperty private Boolean is4dx;

  @JsonProperty private Boolean isScreenx;

  @JsonProperty private Boolean isVision;

  public PatheShowing(
      int cinemaId,
      long movieId,
      long id,
      long start,
      long end,
      Integer is3d,
      Integer nl,
      Integer imax,
      Integer ov,
      Integer hfr,
      Integer isAtmos,
      Integer is4k,
      Integer isLaser,
      Boolean is4dx,
      Boolean isScreenx,
      Boolean isVision) {
    this.cinemaId = cinemaId;
    this.movieId = movieId;
    this.id = id;
    this.start = start;
    this.end = end;
    this.is3d = is3d;
    this.nl = nl;
    this.imax = imax;
    this.ov = ov;
    this.hfr = hfr;
    this.isAtmos = isAtmos;
    this.is4k = is4k;
    this.isLaser = isLaser;
    this.is4dx = is4dx;
    this.isScreenx = isScreenx;
    this.isVision = isVision;
  }

  public PatheShowing() {}

  public long getEnd() {
    return this.end == -1 ? getFakeEnd() : this.end;
  }

  private long getFakeEnd() {
    // 3 hours in millis
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

  public Boolean getIsScreenx() {
    return isScreenx;
  }

  public void setIsScreenx(Boolean isScreenx) {
    this.isScreenx = isScreenx;
  }

  public Boolean getIsVision() {
    return isVision;
  }

  public void setIsVision(Boolean isVision) {
    this.isVision = isVision;
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

  public boolean equalsContent(PatheShowing that) {
    if (this == that) return true;
    if (that == null) return false;
    return cinemaId == that.cinemaId
        && movieId == that.movieId
        && id == that.id
        && start == that.start
        && end == that.end
        && Objects.equals(is3d, that.is3d)
        && Objects.equals(nl, that.nl)
        && Objects.equals(imax, that.imax)
        && Objects.equals(ov, that.ov)
        && Objects.equals(hfr, that.hfr)
        && Objects.equals(isAtmos, that.isAtmos)
        && Objects.equals(is4k, that.is4k)
        && Objects.equals(isLaser, that.isLaser)
        && Objects.equals(is4dx, that.is4dx)
        && Objects.equals(isScreenx, that.isScreenx)
        && Objects.equals(isVision, that.isVision);
  }

  private static final SimpleDateFormat format1 = new SimpleDateFormat("EEE d MMMM HH:mm");
  private static final SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

  public String toMessageString() {
    StringBuilder builder = new StringBuilder();

    if (getStart() != -1L) {
      Cinema cinema = CinemaService.getFirstById(getCinemaId());
      if (cinema != null) {
        format1.setTimeZone(TimeZone.getTimeZone(cinema.getTimezone()));
        format2.setTimeZone(TimeZone.getTimeZone(cinema.getTimezone()));
      }
      builder
          .append(format1.format(new Date(getStart())))
          .append(" - ")
          .append(format2.format(new Date(getEnd())))
          .append(", ");
    }

    if (getImax() == 1) builder.append("IMAX ");

    if (getIsVision()) {
      builder.append("Dolby Cinema ");
    } else {
      if (getIsAtmos() == 1) builder.append("Dolby Atmos ");
      if (getIsLaser() == 1) builder.append("Laser ");
    }

    if (getIs4dx()) builder.append("4DX ");
    if (getIsScreenx()) builder.append("ScreenX ");
    if (getIs4k() == 1) builder.append("4K ");
    if (getIs3d() == 1) builder.append("3D");
    else builder.append("2D");
    if (getHfr() == 1) builder.append(" HFR");
    builder.append(", ");

    builder.append(getUrl());

    return builder.toString();
  }

  public String getUrl() {
    return "https://www.pathe.nl/tickets/start/" + getId();
  }

  @Override
  public int compareTo(PatheShowing o) {
    if (this.getStart() == -1) return 1;
    if (o.getStart() == -1) return -1;
    return Long.compare(this.getStart(), o.getStart());
  }

  @Override
  public String toString() {
    return "PatheShowing{" + "id=" + id + '}';
  }
}
