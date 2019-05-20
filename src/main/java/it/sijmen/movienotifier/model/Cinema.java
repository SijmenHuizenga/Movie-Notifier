package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cinema {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    @JsonProperty
    private float lat, lon;

    @JsonProperty
    private String timezone;

    public Cinema(int id, String name, float lat, float lon, String timezone) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cinema that = (Cinema) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
