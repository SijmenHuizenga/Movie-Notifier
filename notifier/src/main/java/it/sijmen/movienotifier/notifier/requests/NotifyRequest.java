package it.sijmen.movienotifier.notifier.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import it.sijmen.movienotifier.requests.ApikeyRequest;

/**
 * Created by Sijmen on 13-4-2017.
 */
public class NotifyRequest extends ApikeyRequest {

    @JsonProperty
    private String apikey;

    @JsonProperty
    private Recipient recipient;

    @JsonProperty
    private RecipientSettings settings;

    @JsonProperty
    private String message;

    public NotifyRequest() {
        super("F5735948F1A6C8A4F79AD6B80D67FED6BD8F6AED974CC8BF6D5EB7CD291406A1",
                "75B3A3BD95EF49CBE128FD57F991559DD57AD5F4CAAC762645F5A7710889CD52",
                "4F3202D18C43CABD5F512087C1A8AFF97FD9100FFD6401E91AB0CD43A58D39B6",
                "CD0CB3D0AD361ACAC6D23AD03E652321B0EB875762CD7F5E1A7CFE9D4824E13D");
    }

    @Override
    public boolean isValid() {
        return this.authorized() && !empty(message)
                && isValid(recipient) && isValid(settings);
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RecipientSettings getSettings() {
        return settings;
    }

    public void setSettings(RecipientSettings settings) {
        this.settings = settings;
    }
}
