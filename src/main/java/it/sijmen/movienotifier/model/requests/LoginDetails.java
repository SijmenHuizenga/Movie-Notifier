package it.sijmen.movienotifier.model.requests;

import it.sijmen.movienotifier.model.Model;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;

public class LoginDetails extends Model {

    /**
     * The name to login
     */
    @NotBlank
    @Size(min=4, max = 16)
    private String name;

    @NotBlank
    @Size(min=6, max = 128)
    @Field
    private String password;

    public LoginDetails(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
