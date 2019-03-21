package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class NotificationTestdata implements Model {

    @NotBlank
    @JsonProperty
    private String body;

    @NotBlank
    @JsonProperty("watcher-id")
    private String watcherId;

    @NotBlank
    @JsonProperty("watcher-name")
    private String watcherName;

    @NotBlank
    @JsonProperty("match-count")
    private int matchCount;

    @NotBlank
    @JsonProperty
    private int movieid;

    public NotificationTestdata() {
    }

    public NotificationTestdata(String body, String watcherId, String watcherName, int matchCount, int movieid) {
        this.body = body;
        this.watcherId = watcherId;
        this.watcherName = watcherName;
        this.matchCount = matchCount;
        this.movieid = movieid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(String watcherId) {
        this.watcherId = watcherId;
    }

    public String getWatcherName() {
        return watcherName;
    }

    public void setWatcherName(String watcherName) {
        this.watcherName = watcherName;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
}
