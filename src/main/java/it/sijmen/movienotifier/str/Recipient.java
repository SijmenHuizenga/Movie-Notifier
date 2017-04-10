package it.sijmen.movienotifier.str;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class Recipient {

    @JsonProperty
    private String key;
    @JsonProperty
    private String email;
    @JsonProperty
    private String phonenumber;

    public Recipient(String key, String email, String phonenumber) {
        this.key = key;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public boolean isValid(){
        return key != null && email != null && phonenumber != null &&
                !key.isEmpty() && !email.isEmpty() && !phonenumber.isEmpty();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
