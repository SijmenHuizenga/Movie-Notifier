package it.sijmen.movienotifier.str;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class Recipient {

    @JsonProperty
    private String name;
    @JsonProperty
    private String email;
    @JsonProperty
    private String phonenumber;

    public Recipient(String name, String email, String phonenumber) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
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
}
