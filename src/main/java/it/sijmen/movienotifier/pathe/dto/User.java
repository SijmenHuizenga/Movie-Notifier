package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable {

    private static final long serialVersionUID = -1736979765077391802L;

    @JsonProperty
    private String address;
    @JsonProperty
    private Calendar birthDate;
    @JsonProperty
    private Cards cards;
    @JsonProperty
    private String city;
    @JsonProperty
    private String company;
    @JsonProperty
    private String email;
    @JsonProperty
    private String facebookId;
    @JsonProperty
    private String facebookImage;
    @JsonProperty
    private String facebookName;
    @JsonProperty
    private String favoriteSite;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String houseNumber;
    @JsonProperty
    private String houseNumberSuffix;
    @JsonProperty
    private long id;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String middleName;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String zipCode;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumberSuffix() {
        return this.houseNumberSuffix;
    }

    public void setHouseNumberSuffix(String houseNumberSuffix) {
        this.houseNumberSuffix = houseNumberSuffix;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebookImage() {
        return this.facebookImage;
    }

    public void setFacebookImage(String facebookImage) {
        this.facebookImage = facebookImage;
    }

    public String getFacebookName() {
        return this.facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    public String getFacebookId() {
        return this.facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFavoriteSite() {
        return this.favoriteSite;
    }

    public void setFavoriteSite(String favoriteSite) {
        this.favoriteSite = favoriteSite;
    }

    public Cards getCards() {
        return this.cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }
}
