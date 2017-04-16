package it.sijmen.movienotifier.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.Validateble;

import java.util.Objects;

/**
 * Created by Sijmen on 13-4-2017.
 */
public class RecipientSettings implements Validateble {

    @JsonProperty
    private boolean receiveEmails;

    @JsonProperty
    private boolean receiveSms;

    @JsonProperty
    private boolean receiveFbMessge;

    public RecipientSettings(Boolean receiveEmails, boolean receiveSms, boolean receiveFbMessge) {
        this.receiveEmails = receiveEmails;
        this.receiveSms = receiveSms;
        this.receiveFbMessge = receiveFbMessge;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public boolean isReceiveEmails() {
        return receiveEmails;
    }

    public void setReceiveEmails(boolean receiveEmails) {
        this.receiveEmails = receiveEmails;
    }

    public boolean isReceiveSms() {
        return receiveSms;
    }

    public void setReceiveSms(boolean receiveSms) {
        this.receiveSms = receiveSms;
    }

    public boolean isReceiveFbMessge() {
        return receiveFbMessge;
    }

    public void setReceiveFbMessge(boolean receiveFbMessge) {
        this.receiveFbMessge = receiveFbMessge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientSettings that = (RecipientSettings) o;
        return isReceiveSms() == that.isReceiveSms() &&
                isReceiveFbMessge() == that.isReceiveFbMessge() &&
                Objects.equals(isReceiveEmails(), that.isReceiveEmails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isReceiveEmails(), isReceiveSms(), isReceiveFbMessge());
    }

    @Override
    public String toString() {
        return "RecipientSettings{" +
                "receiveEmails=" + receiveEmails +
                ", receiveSms=" + receiveSms +
                ", receiveFbMessge=" + receiveFbMessge +
                '}';
    }
}
