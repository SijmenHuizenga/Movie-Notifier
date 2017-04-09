package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;

public class PatheSchedule implements Serializable {

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

    public int getIsDutch() {
        return this.isDutch;
    }

    public void setIsDutch(int isDutch) {
        this.isDutch = isDutch;
    }

    public int getIsOriginalVersion() {
        return this.isOriginalVersion;
    }

    public void setIsOriginalVersion(int isOriginalVersion) {
        this.isOriginalVersion = isOriginalVersion;
    }

    public int getIsImax() {
        return this.isImax;
    }

    public void setIsImax(int isImax) {
        this.isImax = isImax;
    }

    public int getIs3d() {
        return this.is3d;
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

    public int getIsVIPAvailable() {
        return isVIPAvailable;
    }

    public void setIsVIPAvailable(int isVIPAvailable) {
        this.isVIPAvailable = isVIPAvailable;
    }

    public int getIsHfr() {
        return isHfr;
    }

    public void setIsHfr(int isHfr) {
        this.isHfr = isHfr;
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

    public int getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }

    public int getIsLargeHall() {
        return isLargeHall;
    }

    public void setIsLargeHall(int isLargeHall) {
        this.isLargeHall = isLargeHall;
    }

    public int getIsRooftopHall() {
        return isRooftopHall;
    }

    public void setIsRooftopHall(int isRooftopHall) {
        this.isRooftopHall = isRooftopHall;
    }
}
