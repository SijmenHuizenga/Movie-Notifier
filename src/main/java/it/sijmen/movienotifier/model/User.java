package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.util.ApiKeyHelper;
import it.sijmen.movienotifier.util.ExceptionStringifier;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Where(clause = "active = true")
public class User {

    @Id
    private String id;

    /**
     * The user-friendly name of this recipient. The name must be between 4 and 16 charcters and can only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters
     */
    @NotBlank
    @Size(min=4, max = 16)
    @Pattern(regexp="^([a-z]{4}[a-z0-9]{0,12})$", message = "may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters")
    @Field
    @Indexed(name = "Username Unique", unique = true)
    private String name;

    /**
     * A valid email adres.
     */
    @NotBlank
    @Email
    @Field
    @Indexed(unique = true)
    private String email;

    /**
     * A valid, Global Number as described in RFC 3966 section 5.1.4 (always in the format of +[countrycode][phonenumber])
     */
    @NotBlank
    @Pattern(regexp = "^\\+([0-9]{2}[0-9]{9})$", message = "must be in the format +[countrycode][phonenumber]")
    private String phonenumber;

    /**
     *  The password of the user. The password must at least be 6 characters long and may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks "!@#$%^&*()_-+={}[]:;?><.,"
     */
    @Size(min=6, max = 128)
    @Pattern(regexp = "^([a-zA-z0-9!@#$%^&*()_\\-+={}\\[\\]:;?><.,]+)$", message = "may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks \"!@#$%^&*()_-+={}[]:;?><.,\"")
    @Field
    private String password;

    /**
     * A 64 character string that authenticates the user in api requests
     */
    @Size(max = 64, min = 64, message = "size must be 64")
    @Field
    @Indexed
    private String apikey;

    @Field
    private Date created;

    @Field
    private boolean active;

    public User() {
    }

    /**
     * Used to create a User object of a existing user.
     */
    public User(String id, String name, String email, String phonenumber, String password, String apikey, Date created, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.apikey = apikey;
        this.created = created;
        this.active = active;
    }

    /**
     * Create a new user.
     */
    public User(String name, String email, String phonenumber, String password) throws IllegalArgumentException{
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.apikey = ApiKeyHelper.randomAPIKey();
        this.created = new Date();
    }

    /**
     * Validates the user. When something is wrong a error message is returned.
     * Else null.
     */
    public void validate() throws IllegalArgumentException{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> result = validator.validate(this);
        if(result.size() != 0)
            throw new IllegalArgumentException(ExceptionStringifier.makeNice(result));
    }

    public io.swagger.model.User toSwaggerUser() {
        return new io.swagger.model.User()
                .apikey(this.apikey)
                .email(this.email)
                .name(this.name)
                .phonenumber(this.phonenumber)
                .uuid(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

