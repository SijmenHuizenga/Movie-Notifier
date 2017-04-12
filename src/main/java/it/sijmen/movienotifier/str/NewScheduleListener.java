package it.sijmen.movienotifier.str;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.pathe.dto.PatheSchedule;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class NewScheduleListener {

    @JsonProperty
    private String name = null;

    @JsonProperty
    private int movieid = -1;

    @JsonProperty
    private FilterValue<Integer> cinemaId = null;
    @JsonProperty
    private FilterValue<Calendar> start = null;
    @JsonProperty
    private FilterValue<Calendar> end = null;
    @JsonProperty
    private FilterValue<Integer> status = null;
    @JsonProperty
    private FilterValue<Long> specialId = null;
    @JsonProperty
    private FilterValue<Boolean> imax = null;
    @JsonProperty
    private FilterValue<Boolean> threed = null; //3d
    @JsonProperty
    private FilterValue<Boolean> ov = null;
    @JsonProperty
    private FilterValue<Boolean> nl = null;
    @JsonProperty
    private FilterValue<Boolean> vip = null;
    @JsonProperty
    private FilterValue<Boolean> hfr = null;
    @JsonProperty
    private FilterValue<Boolean> hallIsLarge = null;
    @JsonProperty
    private FilterValue<Boolean> isAtmos = null;
    @JsonProperty
    private FilterValue<Boolean> is4k = null;
    @JsonProperty
    private FilterValue<Boolean> isLaser = null;
    @JsonProperty
    private FilterValue<Boolean> isPremium = null;
    @JsonProperty
    private FilterValue<Boolean> hallIsRooftop = null;

    @JsonProperty
    private ArrayList<String> recipients;

    public boolean isValid(){
        return name == null || name.isEmpty() || movieid == -1 || recipients == null || recipients.isEmpty();
    }

    public boolean accepts(PatheSchedule schedule) {
        return
                accepts(cinemaId, schedule.getCinemaId())
             && accepts(start, schedule.getStartOfShow())
             && accepts(end, schedule.getEndOfShow())
             && accepts(status, schedule.getStatus())
             && accepts(threed, schedule.getIs3d())
             && accepts(specialId, schedule.getSpecialId())
             && accepts(imax, schedule.getIsImax())
             && accepts(ov, schedule.getIsOriginalVersion())
             && accepts(nl, schedule.getIsDutch())
             && accepts(vip, schedule.getIsVIPAvailable())
             && accepts(hfr, schedule.getIsHfr())
             && accepts(hallIsLarge, schedule.getIsLargeHall())
             && accepts(isAtmos, schedule.getIsAtmos())
             && accepts(is4k, schedule.getIs4k())
             && accepts(isLaser, schedule.getIsLaser())
             && accepts(isPremium, schedule.getIsPremium())
             && accepts(hallIsRooftop, schedule.getIsRooftopHall());
    }

    private <T> boolean accepts(FilterValue<T> value, T actualValue) {
        return value == null || value.accepts(actualValue);
    }

    private boolean accepts(FilterValue<Boolean> value, Integer actualValue) {
        return value == null || value.accepts(actualValue == 1);
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

    public FilterValue<Integer> getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(FilterValue<Integer> cinemaId) {
        this.cinemaId = cinemaId;
    }

    public FilterValue<Calendar> getStart() {
        return start;
    }

    public void setStart(FilterValue<Calendar> start) {
        this.start = start;
    }

    public FilterValue<Calendar> getEnd() {
        return end;
    }

    public void setEnd(FilterValue<Calendar> end) {
        this.end = end;
    }

    public FilterValue<Integer> getStatus() {
        return status;
    }

    public void setStatus(FilterValue<Integer> status) {
        this.status = status;
    }

    public FilterValue<Long> getSpecialId() {
        return specialId;
    }

    public void setSpecialId(FilterValue<Long> specialId) {
        this.specialId = specialId;
    }

    public FilterValue<Boolean> getImax() {
        return imax;
    }

    public void setImax(FilterValue<Boolean> imax) {
        this.imax = imax;
    }

    public FilterValue<Boolean> getThreed() {
        return threed;
    }

    public void setThreed(FilterValue<Boolean> threed) {
        this.threed = threed;
    }

    public FilterValue<Boolean> getOv() {
        return ov;
    }

    public void setOv(FilterValue<Boolean> ov) {
        this.ov = ov;
    }

    public FilterValue<Boolean> getNl() {
        return nl;
    }

    public void setNl(FilterValue<Boolean> nl) {
        this.nl = nl;
    }

    public FilterValue<Boolean> getVip() {
        return vip;
    }

    public void setVip(FilterValue<Boolean> vip) {
        this.vip = vip;
    }

    public FilterValue<Boolean> getHfr() {
        return hfr;
    }

    public void setHfr(FilterValue<Boolean> hfr) {
        this.hfr = hfr;
    }

    public FilterValue<Boolean> getHallIsLarge() {
        return hallIsLarge;
    }

    public void setHallIsLarge(FilterValue<Boolean> hallIsLarge) {
        this.hallIsLarge = hallIsLarge;
    }

    public FilterValue<Boolean> getIsAtmos() {
        return isAtmos;
    }

    public void setIsAtmos(FilterValue<Boolean> isAtmos) {
        this.isAtmos = isAtmos;
    }

    public FilterValue<Boolean> getIs4k() {
        return is4k;
    }

    public void setIs4k(FilterValue<Boolean> is4k) {
        this.is4k = is4k;
    }

    public FilterValue<Boolean> getIsLaser() {
        return isLaser;
    }

    public void setIsLaser(FilterValue<Boolean> isLaser) {
        this.isLaser = isLaser;
    }

    public FilterValue<Boolean> getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(FilterValue<Boolean> isPremium) {
        this.isPremium = isPremium;
    }

    public FilterValue<Boolean> getHallIsRooftop() {
        return hallIsRooftop;
    }

    public void setHallIsRooftop(FilterValue<Boolean> hallIsRooftop) {
        this.hallIsRooftop = hallIsRooftop;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }
}
