package it.sijmen.movienotifier.str;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.pathe.dto.PatheSchedule;

import java.util.ArrayList;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class NewScheduleListener {

    @JsonProperty
    private String name = null;

    @JsonProperty
    private int movieid = -1;

    @JsonProperty
    private FilterValue cinemaId = null;
    @JsonProperty
    private FilterValue start = null;
    @JsonProperty
    private FilterValue end = null;
    @JsonProperty
    private FilterValue status = null;
    @JsonProperty
    private FilterValue specialId = null;
    @JsonProperty
    private FilterValue imax = null;
    @JsonProperty
    private FilterValue threed = null;
    @JsonProperty
    private FilterValue ov = null;
    @JsonProperty
    private FilterValue nl = null;
    @JsonProperty
    private FilterValue vip = null;
    @JsonProperty
    private FilterValue hfr = null;
    @JsonProperty
    private FilterValue hallIsLarge = null;
    @JsonProperty
    private FilterValue isAtmos = null;
    @JsonProperty
    private FilterValue is4k = null;
    @JsonProperty
    private FilterValue isLaser = null;
    @JsonProperty
    private FilterValue isPremium = null;
    @JsonProperty
    private FilterValue hallIsRooftop = null;

    @JsonProperty
    private ArrayList<String> recipients;

    public boolean isValid(){
        return name != null && !name.isEmpty() && movieid != -1 && recipients != null && !recipients.isEmpty();
    }

    public boolean accepts(PatheSchedule schedule) {
        //todo
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public FilterValue getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(FilterValue cinemaId) {
        this.cinemaId = cinemaId;
    }

    public FilterValue getStart() {
        return start;
    }

    public void setStart(FilterValue start) {
        this.start = start;
    }

    public FilterValue getEnd() {
        return end;
    }

    public void setEnd(FilterValue end) {
        this.end = end;
    }

    public FilterValue getStatus() {
        return status;
    }

    public void setStatus(FilterValue status) {
        this.status = status;
    }

    public FilterValue getSpecialId() {
        return specialId;
    }

    public void setSpecialId(FilterValue specialId) {
        this.specialId = specialId;
    }

    public FilterValue getImax() {
        return imax;
    }

    public void setImax(FilterValue imax) {
        this.imax = imax;
    }

    public FilterValue getThreed() {
        return threed;
    }

    public void setThreed(FilterValue threed) {
        this.threed = threed;
    }

    public FilterValue getOv() {
        return ov;
    }

    public void setOv(FilterValue ov) {
        this.ov = ov;
    }

    public FilterValue getNl() {
        return nl;
    }

    public void setNl(FilterValue nl) {
        this.nl = nl;
    }

    public FilterValue getVip() {
        return vip;
    }

    public void setVip(FilterValue vip) {
        this.vip = vip;
    }

    public FilterValue getHfr() {
        return hfr;
    }

    public void setHfr(FilterValue hfr) {
        this.hfr = hfr;
    }

    public FilterValue getHallIsLarge() {
        return hallIsLarge;
    }

    public void setHallIsLarge(FilterValue hallIsLarge) {
        this.hallIsLarge = hallIsLarge;
    }

    public FilterValue getIsAtmos() {
        return isAtmos;
    }

    public void setIsAtmos(FilterValue isAtmos) {
        this.isAtmos = isAtmos;
    }

    public FilterValue getIs4k() {
        return is4k;
    }

    public void setIs4k(FilterValue is4k) {
        this.is4k = is4k;
    }

    public FilterValue getIsLaser() {
        return isLaser;
    }

    public void setIsLaser(FilterValue isLaser) {
        this.isLaser = isLaser;
    }

    public FilterValue getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(FilterValue isPremium) {
        this.isPremium = isPremium;
    }

    public FilterValue getHallIsRooftop() {
        return hallIsRooftop;
    }

    public void setHallIsRooftop(FilterValue hallIsRooftop) {
        this.hallIsRooftop = hallIsRooftop;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }

}
