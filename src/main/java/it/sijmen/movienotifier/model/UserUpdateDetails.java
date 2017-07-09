package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.service.notification.validation.ValidNotification;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserUpdateDetails extends Model{

    @Size(min=4, max = 16)
    @Pattern(regexp="^([a-z]{4}[a-z0-9]{0,12})$", message = "may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters")
    private String name = null;

    @Email
    private String email = null;

    @Pattern(regexp = "^\\+([0-9]{2}[0-9]{9})$", message = "must be in the format +[countrycode][phonenumber]")
    private String phonenumber = null;

    @Size(min=6, max = 128)
    @Pattern(regexp = "^([a-zA-z0-9!@#$%^&*()_\\-+={}\\[\\]:;?><.,]+)$", message = "may only contain the letters (a-z), capital letters (A-Z), numbers (0-9) and the following special characters between (and thus except) the quotation marks \"!@#$%^&*()_-+={}[]:;?><.,\"")
    private String password = null;

    @ValidNotification
    private List<String> enabledNotifications;

    public UserUpdateDetails(String name, String email, String phonenumber, String password, List<String> enabledNotifications) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.enabledNotifications = enabledNotifications;
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

    public List<String> getEnabledNotifications() {
        return enabledNotifications;
    }

    public void setEnabledNotifications(List<String> enabledNotifications) {
        this.enabledNotifications = enabledNotifications;
    }
}
