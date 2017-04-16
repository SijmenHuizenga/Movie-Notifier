package it.sijmen.movienotifier.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.Validateble;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class Recipient implements Validateble {

    @JsonProperty
    private UUID uuid;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    private String phonenumber;

    public Recipient(UUID uuid, String name, String email, String phonenumber) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    @Override
    public boolean isValid(){
        return !empty(name) && !empty(email) && !empty(phonenumber);
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(uuid, recipient.uuid) &&
                Objects.equals(getName(), recipient.getName()) &&
                Objects.equals(getEmail(), recipient.getEmail()) &&
                Objects.equals(getPhonenumber(), recipient.getPhonenumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, getName(), getEmail(), getPhonenumber());
    }
}
