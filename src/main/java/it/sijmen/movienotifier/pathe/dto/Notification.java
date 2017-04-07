package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    @JsonProperty
    private String code;
    @JsonProperty
    private int defaultNotifyMinutesBefore;
    @JsonProperty
    private String description;
    @JsonProperty
    private long id;
    @JsonProperty
    private boolean loginRequired;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean notifyMinutesAllowed;
    @JsonProperty
    private int notifyMinutesBefore;
    @JsonProperty
    private String type;

    public Notification() {
        this.notifyMinutesBefore = 10;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNotifyMinutesBefore() {
        return this.notifyMinutesBefore;
    }

    public void setNotifyMinutesBefore(int notifyMinutesBefore) {
        this.notifyMinutesBefore = notifyMinutesBefore;
    }

    public boolean isLoginRequired() {
        return this.loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public boolean isNotifyMinutesAllowed() {
        return this.notifyMinutesAllowed;
    }

    public void setNotifyMinutesAllowed(boolean notifyMinutesAllowed) {
        this.notifyMinutesAllowed = notifyMinutesAllowed;
    }

    public int getDefaultNotifyMinutesBefore() {
        return this.defaultNotifyMinutesBefore > 0 ? this.defaultNotifyMinutesBefore : 10;
    }

    public void setDefaultNotifyMinutesBefore(int defaultNotifyMinutesBefore) {
        this.defaultNotifyMinutesBefore = defaultNotifyMinutesBefore;
    }
}
