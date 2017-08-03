package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.WatcherProps;

public class WatcherDetails {

    /**
     * whether or not the showing is in the original language
     */
    @JsonProperty
    private boolean ov;

    /**
     * whether or not the showing is in the dutch language
     */
    @JsonProperty
    private boolean nl;

    /**
     * whether or not the showing is in
     */
    @JsonProperty
    private boolean imax;

    /**
     * whether or not the showing is in 3D
     */
    @JsonProperty("3d")
    private boolean d3;

    /**
     * whether or not the showing is in HFR
     */
    @JsonProperty
    private boolean hfr;

    /**
     * whether or not the showing is in 4K resolution
     */
    @JsonProperty("4k")
    private boolean k4;

    /**
     * whether or not the showing is projected with a laser projector
     */
    @JsonProperty
    private boolean laser;

    /**
     * whether or not the showing is in D-Box seats
     */
    @JsonProperty
    private boolean dbox;

    /**
     * whether
     * or not the showing uses Dolby Cinema.
     */
    @JsonProperty
    private boolean dolbycinema;

    /**
     * whether or not the showing is in dolbey atmos
     */
    @JsonProperty
    private boolean dolbyatmos;

    public WatcherDetails(boolean ov, boolean nl, boolean imax, boolean d3, boolean hfr, boolean k4, boolean laser, boolean dbox, boolean dolbycinema, boolean dolbyatmos) {
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

    public boolean isOv() {
        return ov;
    }

    public void setOv(boolean ov) {
        this.ov = ov;
    }

    public boolean isNl() {
        return nl;
    }

    public void setNl(boolean nl) {
        this.nl = nl;
    }

    public boolean isImax() {
        return imax;
    }

    public void setImax(boolean imax) {
        this.imax = imax;
    }

    public boolean isD3() {
        return d3;
    }

    public void setD3(boolean d3) {
        this.d3 = d3;
    }

    public boolean isHfr() {
        return hfr;
    }

    public void setHfr(boolean hfr) {
        this.hfr = hfr;
    }

    public boolean isK4() {
        return k4;
    }

    public void setK4(boolean k4) {
        this.k4 = k4;
    }

    public boolean isLaser() {
        return laser;
    }

    public void setLaser(boolean laser) {
        this.laser = laser;
    }

    public boolean isDbox() {
        return dbox;
    }

    public void setDbox(boolean dbox) {
        this.dbox = dbox;
    }

    public boolean isDolbycinema() {
        return dolbycinema;
    }

    public void setDolbycinema(boolean dolbycinema) {
        this.dolbycinema = dolbycinema;
    }

    public boolean isDolbyatmos() {
        return dolbyatmos;
    }

    public void setDolbyatmos(boolean dolbyatmos) {
        this.dolbyatmos = dolbyatmos;
    }

    public WatcherProps toSwaggerProps() {
        WatcherProps props = new WatcherProps();

        props.set3d(this.isD3());
        props.set4k(this.isK4());
        props.setDbox(this.isDbox());
        props.setDolbyatmos(this.isDolbyatmos());
        props.setDolbycinema(this.isDolbycinema());
        props.setHfr(this.isHfr());
        props.setImax(this.isImax());
        props.setLaser(this.isLaser());
        props.setNl(this.isNl());
        props.setOv(this.isOv());

        return props;
    }
}
