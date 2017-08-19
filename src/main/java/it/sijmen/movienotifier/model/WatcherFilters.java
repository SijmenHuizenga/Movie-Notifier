package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * All fields are documented in the Swagger Api Specification in the `/docs` directory.
 */
public class WatcherFilters implements Model {

    @NotBlank
    @JsonProperty
    private String cinemaid;

    @JsonProperty
    @Min(0)
    private long startafter;

    @JsonProperty
    @Min(0)
    private long startbefore;

    @NotNull
    @JsonProperty
    private FilterOption ov;

    @NotNull
    @JsonProperty
    private FilterOption nl;

    @NotNull
    @JsonProperty
    private FilterOption imax;

    @NotNull
    @JsonProperty("3d")
    private FilterOption d3;

    @NotNull
    @JsonProperty
    private FilterOption hfr;

    @NotNull
    @JsonProperty("4k")
    private FilterOption k4;

    @NotNull
    @JsonProperty
    private FilterOption laser;

    @NotNull
    @JsonProperty
    private FilterOption dbox;

    @NotNull
    @JsonProperty
    private FilterOption dolbycinema;

    @NotNull
    @JsonProperty
    private FilterOption dolbyatmos;

    public WatcherFilters(String cinemaid, long startafter, long startbefore, FilterOption ov, FilterOption nl,
                          FilterOption imax, FilterOption d3, FilterOption hfr, FilterOption k4, FilterOption laser,
                          FilterOption dbox, FilterOption dolbycinema, FilterOption dolbyatmos) {
        this.cinemaid = cinemaid;
        this.startafter = startafter;
        this.startbefore = startbefore;
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

    public WatcherFilters() {
    }

    public FilterOption isOv() {
        return ov;
    }

    public void setOv(FilterOption ov) {
        this.ov = ov;
    }

    public FilterOption isNl() {
        return nl;
    }

    public void setNl(FilterOption nl) {
        this.nl = nl;
    }

    public FilterOption isImax() {
        return imax;
    }

    public void setImax(FilterOption imax) {
        this.imax = imax;
    }

    public FilterOption isD3() {
        return d3;
    }

    public void setD3(FilterOption d3) {
        this.d3 = d3;
    }

    public FilterOption isHfr() {
        return hfr;
    }

    public void setHfr(FilterOption hfr) {
        this.hfr = hfr;
    }

    public FilterOption isK4() {
        return k4;
    }

    public void setK4(FilterOption k4) {
        this.k4 = k4;
    }

    public FilterOption isLaser() {
        return laser;
    }

    public void setLaser(FilterOption laser) {
        this.laser = laser;
    }

    public FilterOption isDbox() {
        return dbox;
    }

    public void setDbox(FilterOption dbox) {
        this.dbox = dbox;
    }

    public FilterOption isDolbycinema() {
        return dolbycinema;
    }

    public void setDolbycinema(FilterOption dolbycinema) {
        this.dolbycinema = dolbycinema;
    }

    public FilterOption isDolbyatmos() {
        return dolbyatmos;
    }

    public void setDolbyatmos(FilterOption dolbyatmos) {
        this.dolbyatmos = dolbyatmos;
    }

    public void setStartbefore(long startbefore) {
        this.startbefore = startbefore;
    }

    public void setStartafter(long startafter) {
        this.startafter = startafter;
    }

    public long getStartafter() {
        return startafter;
    }

    public long getStartbefore() {
        return startbefore;
    }

    public String getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(String cinemaid) {
        this.cinemaid = cinemaid;
    }

    @Override
    public String toString() {
        return "WatcherFilters{" +
                "cinemaid='" + cinemaid + '\'' +
                ", startafter=" + startafter +
                ", startbefore=" + startbefore +
                ", ov=" + ov +
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
