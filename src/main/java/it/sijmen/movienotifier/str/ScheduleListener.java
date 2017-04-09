package it.sijmen.movienotifier.str;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Sijmen on 9-4-2017.
 */
public class ScheduleListener {

    @JsonProperty
    private int movieid;

    @JsonProperty
    private FilterValue cinemaId = null;
    @JsonProperty
    private FilterValue movieId = null;
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
    private ArrayList<Recipient> recipients;

}
