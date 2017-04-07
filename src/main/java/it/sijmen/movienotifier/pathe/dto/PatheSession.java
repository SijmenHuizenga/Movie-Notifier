package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;

public class PatheSession implements Serializable {
    private static final long serialVersionUID = -2161013882579840039L;
    public final String TAG;
    @JsonProperty("_deviceToken")
    private String deviceToken;
    @JsonProperty
    private long id;
    @JsonProperty
    private Calendar sessionTimeout;
    @JsonProperty
    private String sessionToken;
    @JsonProperty("_user")
    private User user;
    @JsonProperty("favorites")
    private UserData userData;
    @JsonProperty
    private long userId;

    public PatheSession() {
        this.TAG = getClass().getSimpleName();
        this.sessionTimeout = Calendar.getInstance();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Calendar sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
