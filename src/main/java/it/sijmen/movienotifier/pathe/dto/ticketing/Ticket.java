package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 7270346039882298464L;

    @JsonProperty
    private String authorizationType;
    @JsonProperty
    private boolean canCancel;
    @JsonProperty
    private String description;
    @JsonProperty
    private String detailId;
    @JsonProperty
    private String email;
    @JsonProperty
    private String group;
    @JsonProperty
    private boolean hidePrice;
    @JsonProperty
    private long id;
    @JsonProperty
    private int maxTickets;
    @JsonProperty
    private int minTickets;
    @JsonProperty
    private String name;
    @JsonProperty
    private String pAgeWarning;
    @JsonProperty
    private String paymentRef;
    @JsonProperty
    private int price;
    @JsonProperty
    private String salesChannel;
    @JsonProperty
    private ArrayList<Seated> seats;
    @JsonProperty
    private int seatsPerTicket;
    @JsonProperty
    private String status;

    public static class Seated implements Serializable {
        private static final long serialVersionUID = -3803568600687606247L;
        @JsonProperty
        private String name;
        @JsonProperty
        private String row;

        public Seated(String row, String name) {
            this.row = row;
            this.name = name;
        }

        public String getRow() {
            return this.row;
        }

        public void setRow(String row) {
            this.row = row;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public Ticket(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

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

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthorizationType() {
        return this.authorizationType;
    }

    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIdentifier() {
        return Long.toString(this.id);
    }

    public int getSeatsPerTicket() {
        return this.seatsPerTicket;
    }

    public void setSeatsPerTicket(int seatsPerTicket) {
        this.seatsPerTicket = seatsPerTicket;
    }

    public List<Seated> getSeats() {
        return this.seats;
    }

    public void setSeats(ArrayList<Seated> seats) {
        this.seats = seats;
    }

    public int getMaxTickets() {
        return this.maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public int getMinTickets() {
        return this.minTickets;
    }

    public void setMinTickets(int minTickets) {
        this.minTickets = minTickets;
    }

    public boolean isPriceHidden() {
        return this.hidePrice;
    }

    public void setHidePrice(boolean hidePrice) {
        this.hidePrice = hidePrice;
    }

    public boolean isCanCancel() {
        return this.canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDetailId() {
        return this.detailId;
    }

    public String getPaymentRef() {
        return this.paymentRef;
    }

    public String getSalesChannel() {
        return this.salesChannel;
    }

    public String getEmail() {
        return this.email;
    }

    public String getpAgeWarning() {
        return this.pAgeWarning;
    }

    public boolean isHidePrice() {
        return this.hidePrice;
    }
}
