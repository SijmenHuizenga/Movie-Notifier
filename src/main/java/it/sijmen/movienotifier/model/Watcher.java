package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.validation.date.DateFuture;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Entity
public class Watcher extends Model{

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
    @Range(min=1)
    //todo: custom validator?
    private int movieid;

    /**
     * The unique identifier of the movie to watch for. If empty than every cinema is acceptable.
     */
    @NotBlank
    //todo: custom validator?
    private String cinemaid;

    /**
     * The earliest moment of a showing to watch for.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateFuture
    private Date startAfter;

    /**
     * The latest moment of a showing to watch for.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateFuture
    private Date startBefore;

    /**
     * The details of this watcher.
     */
    @NotNull
    private WatcherDetails details;

    public Watcher(String id, String user, String name, int movieid, String cinemaid, Date startAfter, Date startBefore, WatcherDetails details) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.movieid = movieid;
        this.cinemaid = cinemaid;
        this.startAfter = startAfter;
        this.startBefore = startBefore;
        this.details = details;
    }

    public Watcher() {}

    public void validateUniqueness(WatcherRepository repo) {
        List<String> errors = new ArrayList<>();
        if(repo.countDistinctByUserAndMovieid(this.getUser(), this.getMovieid()) > 0)
            errors.add("Only one watcher per movie per user is allowed.");

        if(errors.size() > 0)
            throw new BadRequestException(errors);
    }

    public io.swagger.model.Watcher toSwaggerWatcher() {
        io.swagger.model.Watcher watcher = new io.swagger.model.Watcher();
        watcher.setUuid(this.getId());
        watcher.setUser(this.getUser());
        watcher.setCinemaid(this.getCinemaid());
        watcher.setMovieid(this.getMovieid());
        watcher.setName(this.getName());
        watcher.setProps(this.getDetails().toSwaggerProps());
        watcher.setStartAfter(new DateTime(this.getStartAfter()));
        watcher.setStartBefore(new DateTime(this.getStartBefore()));
        return watcher;
    }

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

    public Date getStartAfter() {
        return startAfter;
    }

    public void setStartAfter(Date startAfter) {
        this.startAfter = startAfter;
    }

    public Date getStartBefore() {
        return startBefore;
    }

    public void setStartBefore(Date startBefore) {
        this.startBefore = startBefore;
    }

    public WatcherDetails getDetails() {
        return details;
    }

    public void setDetails(WatcherDetails details) {
        this.details = details;
    }
}