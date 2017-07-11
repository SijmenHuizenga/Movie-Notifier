package it.sijmen.movienotifier.model;

public class WatcherDetails {

    /**
     * whether or not the showing is in the original language
     */
    private boolean ov;

    /**
     * whether or not the showing is in the dutch language
     */
    private boolean nl;

    /**
     * whether or not the showing is in
     */
    private boolean imax;

    /**
     * whether or not the showing is in 3D
     */
    private boolean d3;

    /**
     * whether or not the showing is in HFR
     */
    private boolean hfr;

    /**
     * whether or not the showing is in 4K resolution
     */
    private boolean k4;

    /**
     * whether or not the showing is projected with a laser projector
     */
    private boolean laser;

    /**
     * whether or not the showing is in D-Box seats
     */
    private boolean dbox;

    /**
     * whether or not the showing uses Dolby Cinema.
     */
    private boolean dolbycinema;

    /**
     * whether or not the showing is in dolbey atmos
     */
    private boolean dolbyatmos;

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
}
