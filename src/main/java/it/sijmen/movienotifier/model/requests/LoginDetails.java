package it.sijmen.movienotifier.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.model.Model;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDetails implements Model {

    /**
     * The user-friendly name of this recipient. The name must be between 4 and 16 charcters and can only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters
     */
    @NotBlank
    @Size(min=4, max = 16)
    @Pattern(regexp="^([a-z]{4}[a-z0-9]{0,12})$", message = "may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters")
    @Field
    @Indexed(unique = true)
    @JsonProperty
    private String name;

    /**
     *  The password of the user. The password must at least be 6 characters long and may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks "!@#$%^&*()_-+={}[]:;?><.,"
     */
    @NotBlank
    @Size(min=6, max = 128)
    @Pattern(regexp = "^([a-zA-z0-9!@#$%^&*()_\\-+={}\\[\\]:;?><.,]+)$", message = "may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks \"!@#$%^&*()_-+={}[]:;?><.,\"")
    @Field
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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

    @Override
    public String toString() {
        return "LoginDetails{" +
                "name='" + name + '\'' +
                '}';
    }
}
