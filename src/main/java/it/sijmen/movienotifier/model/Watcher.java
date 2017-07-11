package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.model.validation.date.DateFuture;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Document
@Entity
public class Watcher {

    /**
     * The unique identifier that identifies this watcher.
     */
    @Id
    private String id;

    /**
     * The unique identifier of the user that owns this watcher.
     */
    @NotBlank
    @Field
    @Indexed(unique = true)
    private String user;

    /**
     * The displayname of this watcher.
     */
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    /**
     * The unique identifier of the movie to watch for.
     */
    @NotBlank
    @Range
    private int movieid;

    /**
     * The unique identifier of the movie to watch for. If empty than every cinema is acceptable.
     */
    @NotBlank
    private String cinemaid;

    /**
     * The earliest moment of a showing to watch for.
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @DateFuture
    private Date startafter;

    /**
     * The latest moment of a showing to watch for.
     */
    @NotBlank
    @Temporal(TemporalType.DATE)
    @DateFuture
    private Date startBefore;

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

    public Date getStartafter() {
        return startafter;
    }

    public void setStartafter(Date startafter) {
        this.startafter = startafter;
    }

    public Date getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(Date startBefore) {
        this.startBefore = startBefore;
    }
}
