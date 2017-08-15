package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatcherDetails implements Model {

    /**
     * whether or not the showing is in the original language
     */
    @JsonProperty
    private Boolean ov;

    /**
     * whether or not the showing is in the dutch language
     */
    @JsonProperty
    private Boolean nl;

    /**
     * whether or not the showing is in
     */
    @JsonProperty
    private Boolean imax;

    /**
     * whether or not the showing is in 3D
     */
    @JsonProperty("3d")
    private Boolean d3;

    /**
     * whether or not the showing is in HFR
     */
    @JsonProperty
    private Boolean hfr;

    /**
     * whether or not the showing is in 4K resolution
     */
    @JsonProperty("4k")
    private Boolean k4;

    /**
     * whether or not the showing is projected with a laser projector
     */
    @JsonProperty
    private Boolean laser;

    /**
     * whether or not the showing is in D-Box seats
     */
    @JsonProperty
    private Boolean dbox;

    /**
     * whether
     * or not the showing uses Dolby Cinema.
     */
    @JsonProperty
    private Boolean dolbycinema;

    /**
     * whether or not the showing is in dolbey atmos
     */
    @JsonProperty
    private Boolean dolbyatmos;

    public WatcherDetails(Boolean ov, Boolean nl, Boolean imax, Boolean d3, Boolean hfr, Boolean k4, Boolean laser, Boolean dbox, Boolean dolbycinema, Boolean dolbyatmos) {
        this.ov = ov;
        this.nl = nl;
        this.imax = imax;
        this.d3 = d3;
        this.hfr = hfr;
        this.k4 = k4;
        this.laser = laser;
        this.dbox = dbox;
        this.dolbycinema = dolbycinema;
        this.dolbyatmos = dolbyatmos;
    }

    public WatcherDetails() {
    }

    public Boolean isOv() {
        return ov;
    }

    public void setOv(Boolean ov) {
        this.ov = ov;
    }

    public Boolean isNl() {
        return nl;
    }

    public void setNl(Boolean nl) {
        this.nl = nl;
    }

    public Boolean isImax() {
        return imax;
    }

    public void setImax(Boolean imax) {
        this.imax = imax;
    }

    public Boolean isD3() {
        return d3;
    }

    public void setD3(Boolean d3) {
        this.d3 = d3;
    }

    public Boolean isHfr() {
        return hfr;
    }

    public void setHfr(Boolean hfr) {
        this.hfr = hfr;
    }

    public Boolean isK4() {
        return k4;
    }

    public void setK4(Boolean k4) {
        this.k4 = k4;
    }

    public Boolean isLaser() {
        return laser;
    }

    public void setLaser(Boolean laser) {
        this.laser = laser;
    }

    public Boolean isDbox() {
        return dbox;
    }

    public void setDbox(Boolean dbox) {
        this.dbox = dbox;
    }

    public Boolean isDolbycinema() {
        return dolbycinema;
    }

    public void setDolbycinema(Boolean dolbycinema) {
        this.dolbycinema = dolbycinema;
    }

    public Boolean isDolbyatmos() {
        return dolbyatmos;
    }

    public void setDolbyatmos(Boolean dolbyatmos) {
        this.dolbyatmos = dolbyatmos;
    }

    @Override
    public String toString() {
        return "WatcherDetails{" +
                "ov=" + ov +
                ", nl=" + nl +
                ", imax=" + imax +
                ", d3=" + d3 +
                ", hfr=" + hfr +
                ", k4=" + k4 +
                ", laser=" + laser +
                ", dbox=" + dbox +
                ", dolbycinema=" + dolbycinema +
                ", dolbyatmos=" + dolbyatmos +
                '}';
    }
}
