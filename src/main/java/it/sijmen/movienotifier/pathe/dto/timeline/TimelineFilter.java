package it.sijmen.movienotifier.pathe.dto.timeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.pathe.dto.PatheVersion;

import java.util.ArrayList;

public class TimelineFilter {

    public static final int CINEMA_FILTER_AZ = 1;
    public static final int CINEMA_FILTER_CINEMAS = 4;
    public static final int CINEMA_FILTER_GPS = 2;
    public static final int CINEMA_FILTER_MY_CINEMAS = 3;
    public static final int MOVIE_ORDER_AZ = 5;
    public static final int MOVIE_ORDER_UPCOMING = 1;
    public static final int TIMELINE_DISPLAY_BLOCKS = 1;
    public static final int TIMELINE_DISPLAY_LIST = 2;
    public static final int TIMELINE_DISPLAY_LIST_WITH_SEPARATION = 3;

    @JsonProperty
    private int ageFilter;
    @JsonProperty
    private int cinemaFilter;
    @JsonProperty
    private int cinemaOrder;
    @JsonProperty
    private ArrayList<Long> cinemasSelected;
    @JsonProperty
    private ArrayList<Long> genresFilter;
    @JsonProperty
    private int kijkwijzerFilter;
    @JsonProperty
    private ArrayList<Long> specialsFilter;
    @JsonProperty
    private int timelineDisplay;
    @JsonProperty
    private PatheVersion versionFilter;

    public TimelineFilter() {
        this.timelineDisplay = TIMELINE_DISPLAY_LIST_WITH_SEPARATION;
        this.cinemaOrder = TIMELINE_DISPLAY_LIST;
        this.cinemaFilter = TIMELINE_DISPLAY_LIST;
        this.ageFilter = TIMELINE_DISPLAY_LIST_WITH_SEPARATION;
        this.versionFilter = null;
        this.kijkwijzerFilter = 63;
        this.versionFilter = PatheVersion.getDefaultFilter();
        this.cinemasSelected = new ArrayList<>();
        this.specialsFilter = new ArrayList<>();
        this.genresFilter = new ArrayList<>();
    }

    public boolean equals(Object o) {
        if (!(o instanceof TimelineFilter)) {
            return super.equals(o);
        }
        TimelineFilter other = (TimelineFilter) o;
        return this.cinemaOrder == other.cinemaOrder && this.cinemaFilter == other.cinemaFilter && this.ageFilter == other.ageFilter && this.versionFilter == other.versionFilter && getKijkwijzerFilter() == other.getKijkwijzerFilter() && this.cinemasSelected.equals(other.cinemasSelected) && this.specialsFilter.equals(other.specialsFilter) && this.genresFilter.equals(other.genresFilter);
    }

    public boolean isDefault() {
        return equals(new TimelineFilter());
    }

    public int getCinemaOrder() {
        return this.cinemaOrder;
    }

    public int getCinemaFilter() {
        return this.cinemaFilter;
    }

    public void setCinemaFilter(int cinemaFilter) {
        this.cinemaFilter = cinemaFilter;
        if (cinemaFilter == TIMELINE_DISPLAY_BLOCKS) {
            this.cinemaOrder = TIMELINE_DISPLAY_BLOCKS;
        }
        if (cinemaFilter == TIMELINE_DISPLAY_LIST) {
            this.cinemaOrder = TIMELINE_DISPLAY_LIST;
        }
    }

    public ArrayList<Long> getCinemasSelected() {
        return this.cinemasSelected;
    }

    public void setCinemasSelected(ArrayList<Long> cinemasSelected) {
        this.cinemasSelected = cinemasSelected;
    }

    public int getMovieOrder() {
        return getTimelineDisplay() == TIMELINE_DISPLAY_BLOCKS ? MOVIE_ORDER_AZ : TIMELINE_DISPLAY_BLOCKS;
    }

    public ArrayList<Long> getSpecialsFilter() {
        return this.specialsFilter;
    }

    public void setSpecialsFilter(ArrayList<Long> specialsFilter) {
        this.specialsFilter = specialsFilter;
    }

    public ArrayList<Long> getGenresFilter() {
        return this.genresFilter;
    }

    public void setGenresFilter(ArrayList<Long> genresFilter) {
        this.genresFilter = genresFilter;
    }

    public PatheVersion getVersionFilter() {
        return this.versionFilter;
    }

    public void setVersionFilter(PatheVersion versionFilter) {
        this.versionFilter = versionFilter;
    }

    public int getAgeFilter() {
        return this.ageFilter;
    }

    public void setAgeFilter(int ageFilter) {
        this.ageFilter = ageFilter;
    }

    public int getKijkwijzerFilter() {
        return this.kijkwijzerFilter;
    }

    public void setKijkwijzerFilter(int kijkwijzerFilter) {
        this.kijkwijzerFilter = kijkwijzerFilter;
    }

    public int getTimelineDisplay() {
        return this.timelineDisplay;
    }

    public void setTimelineDisplay(int timelineDisplay) {
        this.timelineDisplay = timelineDisplay;
    }

}
