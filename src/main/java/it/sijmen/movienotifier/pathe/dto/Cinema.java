package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Cinema implements Serializable {

    private static final long serialVersionUID = -4593722361788767520L;

    public final String TAG;
    @JsonProperty
    private String address;
    @JsonProperty
    private String alertMessage;
    @JsonProperty
    private City city;
    @JsonProperty
    private String code;
    @JsonProperty
    private String description;
    @JsonProperty
    private String emailCinema;
    @JsonProperty
    private String emailHrm;
    @JsonProperty
    private String emailReservation;
    @JsonProperty
    private List<Experience> experiences;
    @JsonProperty
    private PatheKeyValueMap factFigures;
    @JsonProperty
    private String googleMapsImage;
    @JsonProperty
    private PatheKeyValueMap handicap;
    @JsonProperty
    private long id;
    @JsonProperty
    private boolean imaxAvailable;
    @JsonProperty
    private double lat;
    @JsonProperty
    private double lon;
    @JsonProperty
    private String name;
    @JsonProperty
    private PatheKeyValueMap openingHours;
    @JsonProperty
    private List<Parking> parking;
    @JsonProperty
    private String phonenumber;
    @JsonProperty
    private String phonenumberText;
    @JsonProperty
    private PatheKeyValueMap publicTransport;
    @JsonProperty
    private String routeDescriptionImage;
    @JsonProperty
    private boolean scannerAvailable;
    @JsonProperty
    private int showDurationExtraMinutes;
    @JsonProperty
    private boolean wifiAnonymousAccess;
    @JsonProperty
    private boolean wifiShowContentLinks;
    @JsonProperty
    private String zipcode;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Cinema() {
        this.TAG = getClass().getSimpleName();
    }


    public int describeContents() {
        return 0;
    }

    public String getAlertMessage() {
        return this.alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumberText() {
        return this.phonenumberText;
    }

    public void setPhonenumberText(String phonenumberText) {
        this.phonenumberText = phonenumberText;
    }

    public String getRouteDescriptionImage() {
        return this.routeDescriptionImage;
    }

    public void setRouteDescriptionImage(String routeDescriptionImage) {
        this.routeDescriptionImage = routeDescriptionImage;
    }

    public String getGoogleMapsImage() {
        return this.googleMapsImage;
    }

    public void setGoogleMapsImage(String googleMapsImage) {
        this.googleMapsImage = googleMapsImage;
    }

    public String getEmailCinema() {
        return this.emailCinema;
    }

    public void setEmailCinema(String emailCinema) {
        this.emailCinema = emailCinema;
    }

    public String getEmailReservation() {
        return this.emailReservation;
    }

    public void setEmailReservation(String emailReservation) {
        this.emailReservation = emailReservation;
    }

    public String getEmailHrm() {
        return this.emailHrm;
    }

    public void setEmailHrm(String emailHrm) {
        this.emailHrm = emailHrm;
    }

    public boolean isImaxAvailable() {
        return this.imaxAvailable;
    }

    public void setImaxAvailable(boolean imaxAvailable) {
        this.imaxAvailable = imaxAvailable;
    }

    public int getShowDurationExtraMinutes() {
        return this.showDurationExtraMinutes;
    }

    public void setShowDurationExtraMinutes(int showDurationExtraMinutes) {
        this.showDurationExtraMinutes = showDurationExtraMinutes;
    }

    public boolean isWifiAnonymousAccess() {
        return this.wifiAnonymousAccess;
    }

    public void setWifiAnonymousAccess(boolean wifiAnonymousAccess) {
        this.wifiAnonymousAccess = wifiAnonymousAccess;
    }

    public boolean isWifiShowContentLinks() {
        return this.wifiShowContentLinks;
    }

    public void setWifiShowContentLinks(boolean wifiShowContentLinks) {
        this.wifiShowContentLinks = wifiShowContentLinks;
    }

    public PatheKeyValueMap getOpeningHours() {
        return this.openingHours;
    }

    public void setOpeningHours(PatheKeyValueMap openingHours) {
        this.openingHours = openingHours;
    }

    public PatheKeyValueMap getFactFigures() {
        return this.factFigures;
    }

    public void setFactFigures(PatheKeyValueMap factFigures) {
        this.factFigures = factFigures;
    }

    public PatheKeyValueMap getHandicap() {
        return this.handicap;
    }

    public void setHandicap(PatheKeyValueMap handicap) {
        this.handicap = handicap;
    }

    public PatheKeyValueMap getPublicTransport() {
        return this.publicTransport;
    }

    public void setPublicTransport(PatheKeyValueMap publicTransport) {
        this.publicTransport = publicTransport;
    }

    public List<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Parking> getParking() {
        return this.parking;
    }

    public void setParking(List<Parking> parking) {
        this.parking = parking;
    }

    public boolean isScannerAvailable() {
        return this.scannerAvailable;
    }

    public void setScannerAvailable(boolean scannerAvailable) {
        this.scannerAvailable = scannerAvailable;
    }
}
