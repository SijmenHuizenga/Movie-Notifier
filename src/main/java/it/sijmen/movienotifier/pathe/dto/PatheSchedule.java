package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class PatheSchedule implements Serializable, Cloneable {

    private static final long serialVersionUID = -4593722361788767522L;

    private Cinema cinema;

    @JsonProperty
    private int cinemaId;
    @JsonProperty("end")
    private Calendar endOfShow;
    @JsonProperty("3d")
    private int is3d;
    @JsonProperty("nl")
    private int isDutch;
    @JsonProperty("imax")
    private int isImax;
    @JsonProperty("ov")
    private int isOriginalVersion;
    @JsonProperty("vip")
    private int isVIPAvailable;
    @JsonProperty("hfr")
    private int isHfr;
    @JsonProperty("isAtmos")
    private int isAtmos;
    @JsonProperty("is4k")
    private int is4k;
    @JsonProperty("isLaser")
    private int isLaser;
    @JsonProperty("isPremium")
    private int isPremium;
    @JsonProperty("hallIsLarge")
    private int isLargeHall;
    @JsonProperty("hallIsRooftop")
    private int isRooftopHall;
    @JsonProperty
    private long movieId;
    @JsonProperty("id")
    private long scheduleId;
    @JsonProperty
    private long specialId;
    @JsonProperty("start")
    private Calendar startOfShow;
    @JsonProperty
    private int status;


    public long getId() {
        return this.scheduleId;
    }

    public void setID(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getmovieID() {
        return this.movieId;
    }

    public void setMovieID(long movieId) {
        this.movieId = movieId;
    }

    public Calendar getStartOfShow() {
        return this.startOfShow;
    }

    public void setStartOfShow(Calendar cal) {
        this.startOfShow = cal;
    }

    public void setEndOfShow(Calendar cal) {
        this.endOfShow = cal;
    }

    public Calendar getEndOfShow() {
        return this.endOfShow;
    }

    public Calendar getFakeEndOfShow() {
        Calendar fakeEndOfShow = Calendar.getInstance();
        fakeEndOfShow.setTimeInMillis(getStartOfShow().getTimeInMillis());
        fakeEndOfShow.add(11, 1);
        return fakeEndOfShow;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCinemaId() {
        return this.cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public boolean getIsDutch() {
        return this.isDutch == 1;
    }

    public void setIsDutch(int isDutch) {
        this.isDutch = isDutch;
    }

    public boolean getIsOriginalVersion() {
        return this.isOriginalVersion == 1;
    }

    public void setIsOriginalVersion(int isOriginalVersion) {
        this.isOriginalVersion = isOriginalVersion;
    }

    public boolean getIsImax() {
        return this.isImax == 1;
    }

    public void setIsImax(int isImax) {
        this.isImax = isImax;
    }

    public boolean getIs3d() {
        return this.is3d == 1;
    }

    public void setIs3d(int is3d) {
        this.is3d = is3d;
    }

    public Cinema getCinema() {
        return this.cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public long getSpecialId() {
        return this.specialId;
    }

    public void setSpecialId(long specialId) {
        this.specialId = specialId;
    }

    public boolean getIsVIPAvailable() {
        return isVIPAvailable == 1;
    }

    public void setIsVIPAvailable(int isVIPAvailable) {
        this.isVIPAvailable = isVIPAvailable;
    }

    public boolean getIsHfr() {
        return isHfr == 1;
    }

    public void setIsHfr(int isHfr) {
        this.isHfr = isHfr;
    }

    public boolean getIsAtmos() {
        return isAtmos == 1;
    }

    public void setIsAtmos(int isAtmos) {
        this.isAtmos = isAtmos;
    }

    public boolean getIs4k() {
        return is4k == 1;
    }

    public void setIs4k(int is4k) {
        this.is4k = is4k;
    }

    public boolean getIsLaser() {
        return isLaser == 1;
    }

    public void setIsLaser(int isLaser) {
        this.isLaser = isLaser;
    }

    public boolean getIsPremium() {
        return isPremium == 1;
    }

    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }

    public boolean getIsLargeHall() {
        return isLargeHall == 1;
    }

    public void setIsLargeHall(int isLargeHall) {
        this.isLargeHall = isLargeHall;
    }

    public boolean getIsRooftopHall() {
        return isRooftopHall == 1;
    }

    public void setIsRooftopHall(int isRooftopHall) {
        this.isRooftopHall = isRooftopHall;
    }

    @Override
    public String toString() {
        return "PatheSchedule{" +
                "cinema=" + cinema +
                ", cinemaId=" + cinemaId +
                ", endOfShow=" + endOfShow +
                ", is3d=" + is3d +
                ", isDutch=" + isDutch +
                ", isImax=" + isImax +
                ", isOriginalVersion=" + isOriginalVersion +
                ", movieId=" + movieId +
                ", scheduleId=" + scheduleId +
                ", specialId=" + specialId +
                ", startOfShow=" + startOfShow +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatheSchedule that = (PatheSchedule) o;
        return scheduleId == that.scheduleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }


}
