package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parking {

    @JsonProperty
    private String address;
    @JsonProperty
    private String googleMapsImage;
    @JsonProperty
    private long id;
    @JsonProperty
    private double lat;
    @JsonProperty
    private double lon;
    @JsonProperty
    private String name;
    @JsonProperty
    private double rate;
    @JsonProperty
    private String remarks;
    @JsonProperty
    private int seqno;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGoogleMapsImage() {
        return this.googleMapsImage;
    }

    public void setGoogleMapsImage(String googleMapsImage) {
        this.googleMapsImage = googleMapsImage;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getSeqno() {
        return this.seqno;
    }

}
