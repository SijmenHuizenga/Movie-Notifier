package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.controllers.ModelUpdater;
import org.hibernate.validator.constraints.NotBlank;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * All fields are documented in the Swagger Api Specification in the `/docs` directory.
 */
@Document
@Entity
public class Watcher implements Model {

    /**
     * The mongodb database id. This field is not exposed and is only used as a mongodb technical thingy.
     */
    @Id
    private String id;

    @Field
    @Indexed(unique = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private String uuid;

    @NotBlank
    @JsonProperty
    private String userid;

    @NotBlank
    @Size(min = 3, max = 50)
    @JsonProperty
    private String name;

    @Min(1)
    @JsonProperty
    private int movieid;

    @NotNull
    @Min(0)
    @JsonProperty
    private long begin;

    @NotNull
    @Min(0)
    @JsonProperty
    private long end;

    @JsonProperty
    @Valid
    @NotNull
    @ModelUpdater.RecursiveUpdate
    private WatcherFilters filters;

    public Watcher(String id, String userid, String name, int movieid, long begin, long end, WatcherFilters filters) {
        this.uuid = id;
        this.userid = userid;
        this.name = name;
        this.movieid = movieid;
        this.begin = begin;
        this.end = end;
        this.filters = filters;
    }

    public Watcher(Watcher watcher){
        this.uuid = watcher.uuid;
        this.userid = watcher.userid;
        this.name = watcher.name;
        this.movieid = watcher.movieid;
        this.begin = watcher.begin;
        this.end = watcher.end;
        this.filters = new WatcherFilters(watcher.filters);
    }

    public Watcher() {}

    @AssertTrue(message="must be later than begin")
    private boolean isEnd() {
        return this.end > this.begin;
    }

    @AssertTrue(message="between begin and end must be smaller than 1 month")
    private boolean isTime() {
        return Math.abs(this.end - this.begin) < 2629746000L; // 1 month
    }

    public String getId() {
        return uuid;
    }

    public void setId(String id) {
        this.uuid = id;
    }

    @JsonIgnore
    public String getCinemaPrefix() {
        return this.filters.getCinemaid().replaceAll("[0-9]", "");
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public WatcherFilters getFilters() {
        return filters;
    }

    public void setFilters(@Nullable WatcherFilters filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "Watcher{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", movieid=" + movieid +
                ", begin=" + begin +
                ", end=" + end +
                ", filters=" + filters +
                '}';
    }
}