package it.sijmen.movienotifier.notifier.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import it.sijmen.movienotifier.notifier.notifiers.NotificationModule;
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

    @Inject
    public NotifyRequest(@Named("api-keys") String[] keys) {
        super(keys);
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
