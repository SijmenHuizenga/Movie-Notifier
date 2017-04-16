package it.sijmen.movienotifier.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.Validateble;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class Notifier implements Validateble {

    @JsonProperty
    private UUID uuid;

    @JsonProperty
    private String name = null;

    @JsonProperty
    private int movieid = -1;

    @JsonProperty
    private ArrayList<UUID> recipients;

    @JsonProperty
    private Integer cinemaId = null;

    @JsonProperty
    private Calendar startAfter = null;

    @JsonProperty
    private Calendar startBefore = null;

    @JsonProperty
    private Integer status = null;

    @JsonProperty
    private Long specialId = null;

    @JsonProperty
    private Boolean imax = null;

    @JsonProperty
    private Boolean threed = null; //3d

    @JsonProperty
    private Boolean ov = null;

    @JsonProperty
    private Boolean nl = null;

    @JsonProperty
    private Boolean vip = null;

    @JsonProperty
    private Boolean hfr = null;

    @JsonProperty
    private Boolean hallIsLarge = null;

    @JsonProperty
    private Boolean isAtmos = null;

    @JsonProperty
    private Boolean is4k = null;

    @JsonProperty
    private Boolean isLaser = null;

    @JsonProperty
    private Boolean isPremium = null;

    @JsonProperty
    private Boolean hallIsRooftop = null;

    @Override
    public boolean isValid(){
        return uuid != null && !empty(name) && movieid != -1 && recipients != null && !recipients.isEmpty();
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

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Calendar getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(Calendar startAfter) {
        this.startAfter = startAfter;
    }

    public Calendar getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(Calendar startBefore) {
        this.startBefore = startBefore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public Boolean getImax() {
        return imax;
    }

    public void setImax(Boolean imax) {
        this.imax = imax;
    }

    public Boolean getThreed() {
        return threed;
    }

    public void setThreed(Boolean threed) {
        this.threed = threed;
    }

    public Boolean getOv() {
        return ov;
    }

    public void setOv(Boolean ov) {
        this.ov = ov;
    }

    public Boolean getNl() {
        return nl;
    }

    public void setNl(Boolean nl) {
        this.nl = nl;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Boolean getHfr() {
        return hfr;
    }

    public void setHfr(Boolean hfr) {
        this.hfr = hfr;
    }

    public Boolean getHallIsLarge() {
        return hallIsLarge;
    }

    public void setHallIsLarge(Boolean hallIsLarge) {
        this.hallIsLarge = hallIsLarge;
    }

    public Boolean getAtmos() {
        return isAtmos;
    }

    public void setAtmos(Boolean atmos) {
        isAtmos = atmos;
    }

    public Boolean getIs4k() {
        return is4k;
    }

    public void setIs4k(Boolean is4k) {
        this.is4k = is4k;
    }

    public Boolean getLaser() {
        return isLaser;
    }

    public void setLaser(Boolean laser) {
        isLaser = laser;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public Boolean getHallIsRooftop() {
        return hallIsRooftop;
    }

    public void setHallIsRooftop(Boolean hallIsRooftop) {
        this.hallIsRooftop = hallIsRooftop;
    }

    public ArrayList<UUID> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<UUID> recipients) {
        this.recipients = recipients;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifier that = (Notifier) o;
        return getMovieid() == that.getMovieid() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getRecipients(), that.getRecipients()) &&
                Objects.equals(getCinemaId(), that.getCinemaId()) &&
                Objects.equals(getStartAfter(), that.getStartAfter()) &&
                Objects.equals(getStartBefore(), that.getStartBefore()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getSpecialId(), that.getSpecialId()) &&
                Objects.equals(getImax(), that.getImax()) &&
                Objects.equals(getThreed(), that.getThreed()) &&
                Objects.equals(getOv(), that.getOv()) &&
                Objects.equals(getNl(), that.getNl()) &&
                Objects.equals(getVip(), that.getVip()) &&
                Objects.equals(getHfr(), that.getHfr()) &&
                Objects.equals(getHallIsLarge(), that.getHallIsLarge()) &&
                Objects.equals(isAtmos, that.isAtmos) &&
                Objects.equals(getIs4k(), that.getIs4k()) &&
                Objects.equals(isLaser, that.isLaser) &&
                Objects.equals(isPremium, that.isPremium) &&
                Objects.equals(getHallIsRooftop(), that.getHallIsRooftop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMovieid(), getRecipients(), getCinemaId(), getStartAfter(), getStartBefore(), getStatus(), getSpecialId(), getImax(), getThreed(), getOv(), getNl(), getVip(), getHfr(), getHallIsLarge(), isAtmos, getIs4k(), isLaser, isPremium, getHallIsRooftop());
    }

    @Override
    public String toString() {
        return "NewScheduleListener{" +
                "name='" + name + '\'' +
                ", movieid=" + movieid +
                ", recipients=" + recipients +
                ", cinemaId=" + cinemaId +
                ", startAfter=" + startAfter +
                ", startBefore=" + startBefore +
                ", status=" + status +
                ", specialId=" + specialId +
                ", imax=" + imax +
                ", threed=" + threed +
                ", ov=" + ov +
                ", nl=" + nl +
                ", vip=" + vip +
                ", hfr=" + hfr +
                ", hallIsLarge=" + hallIsLarge +
                ", isAtmos=" + isAtmos +
                ", is4k=" + is4k +
                ", isLaser=" + isLaser +
                ", isPremium=" + isPremium +
                ", hallIsRooftop=" + hallIsRooftop +
                '}';
    }
}
