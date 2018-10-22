package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.validation.notification.ValidNotification;
import it.sijmen.movienotifier.repositories.UserRepository;

/**
 * All fields are documented in the Swagger Api Specification in the `/docs` directory.
 */
@Document
@Entity
public class User implements Model {

    /**
     * The mongodb database id. This field is not exposed and is only used as a mongodb technical thingy
     */
    @Id
    private String id;

    @Field
    @Indexed(unique = true)
    @JsonProperty(value="id", access = JsonProperty.Access.READ_ONLY)
    private String uuid;

    @NotBlank
    @Size(min=4, max = 16)
    @Pattern(regexp="^([a-z]{4}[a-z0-9]{0,12})$", message = "may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters")
    @Field
    @Indexed(unique = true)
    @JsonProperty
    private String name;


    @NotBlank
    @Email
    @Field
    @Indexed(unique = true)
    @JsonProperty
    private String email;

    @NotBlank
    @Field
    @Pattern(regexp = "^\\+([0-9]{2}[0-9]{9})$", message = "must be in the format +[countrycode][phonenumber]")
    @JsonProperty
    private String phonenumber;

    @NotBlank
    @Size(min=8, max = 128)
    @Pattern(regexp = "^([a-zA-z0-9!@#$%^&*()_\\-+={}\\[\\]:;?><.,]+)$", message = "may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks \"!@#$%^&*()_-+={}[]:;?><.,\"")
    @Field
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(max = 64, min = 64, message = "size must be 64")
    @Field
    @Indexed(unique=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String apikey;

    @Field
    @JsonIgnore
    private Date created;

    @Field
    @ValidNotification
    @JsonProperty("notifications")
    private List<String> enabledNotifications;

    public User() {
    }

    public User(String id, String name, String email, String phonenumber, String password, String apikey,
                Date created, List<String> enabledNotifications) {
        this.uuid = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.apikey = apikey;
        this.created = created;
        this.enabledNotifications = enabledNotifications;
    }

    public void validateUniqueness(UserRepository userRepository) {
        List<String> errors = new ArrayList<>();
        if(userRepository.getAllByName(getName()).stream().filter(o -> !o.getId().equals(this.getId())).count() > 0)
            errors.add("The given username is already in use.");
        if(userRepository.getAllByEmail(getEmail()).stream().filter(o -> !o.getId().equals(this.getId())).count() > 0)
            errors.add("The given email is already in use.");
        if(!errors.isEmpty())
            throw new BadRequestException(errors);
    }

    public String getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public List<String> getEnabledNotifications() {
        return enabledNotifications;
    }

    public void setEnabledNotifications(List<String> enabledNotifications) {
        this.enabledNotifications = enabledNotifications;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setId(String id) {
        this.uuid = id;
    }
}

