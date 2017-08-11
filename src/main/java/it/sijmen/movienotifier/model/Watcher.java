package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.model.validation.date.DateFuture;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Document
@Entity
public class Watcher extends Model{

    /**
     * The unique identifier that identifies this watcher.
     */
    @Id
    @JsonProperty(value = "uuid", access = JsonProperty.Access.READ_ONLY)
    private String id;

    /**
     * The unique identifier of the user that owns this watcher.
     */
    @NotBlank
    @Field
    @Indexed(unique = true)
    @JsonProperty
    private String user;

    /**
     * The displayname of this watcher.
     */
    @NotBlank
    @Length(min = 3, max = 50)
    @JsonProperty
    private String name;

    /**
     * The unique identifier of the movie to watch for.
     */
    @Range(min=1)
    @JsonProperty
    //todo: custom validator?
    private int movieid;

    /**
     * The unique identifier of the movie to watch for. If empty than every cinema is acceptable.
     */
    @NotBlank
    @JsonProperty
    //todo: custom validator?
    private String cinemaid;

    /**
     * The earliest moment of a showing to watch for.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateFuture
    @JsonProperty
    private long startAfter;

    /**
     * The latest moment of a showing to watch for.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateFuture
    @JsonProperty
    private long startBefore;

    /**
     * The props of this watcher.
     */
    @JsonProperty
    @Valid
    @Nullable
    private WatcherDetails props;

    public Watcher(String id, String user, String name, int movieid, String cinemaid, long startAfter, long startBefore, WatcherDetails props) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.movieid = movieid;
        this.cinemaid = cinemaid;
        this.startAfter = startAfter;
        this.startBefore = startBefore;
        this.props = props;
    }

    public Watcher() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(String cinemaid) {
        this.cinemaid = cinemaid;
    }

    public long getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(long startAfter) {
        this.startAfter = startAfter;
    }

    public long getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(long startBefore) {
        this.startBefore = startBefore;
    }

    public WatcherDetails getProps() {
        return props;
    }

    public void setProps(WatcherDetails props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Watcher{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", movieid=" + movieid +
                ", cinemaid='" + cinemaid + '\'' +
                ", startAfter=" + startAfter +
                ", startBefore=" + startBefore +
                ", props=" + props +
                '}';
    }
}