package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Card implements Serializable {

    public static final transient String CARD_TYPE_MOVIECARD = "MovieCard";
    public static final transient String CARD_TYPE_UNLIMITED = "Unlimited";
    public static final transient String CARD_TYPE_UNLIMITEDGOLD = "UnlimitedGold";

    private static final long serialVersionUID = -3155958483883723606L;

    @JsonProperty
    private int balance;
    @JsonProperty
    private String cardType;
    @JsonProperty
    private String condition;
    @JsonProperty
    private String description;
    @JsonProperty
    private String expiryDate;
    @JsonProperty
    private long id;
    @JsonProperty
    private String instruction;
    @JsonProperty
    private String name;
    @JsonProperty
    private String passbookMovieCardUrl;
    @JsonProperty
    private String pin;
    @JsonProperty
    private int points;
    @JsonProperty
    private String startDate;
    @JsonProperty
    private String state;
    @JsonProperty
    private String stateComment;
    @JsonProperty
    private String terms;
    @JsonProperty
    private int tickets;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateComment() {
        return this.stateComment;
    }

    public void setStateComment(String stateComment) {
        this.stateComment = stateComment;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTickets() {
        return this.tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPassbookMovieCardUrl() {
        return this.passbookMovieCardUrl;
    }

    public void setPassbookMovieCardUrl(String passbookMovieCardUrl) {
        this.passbookMovieCardUrl = passbookMovieCardUrl;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTerms() {
        return this.terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int describeContents() {
        return 0;
    }
}
