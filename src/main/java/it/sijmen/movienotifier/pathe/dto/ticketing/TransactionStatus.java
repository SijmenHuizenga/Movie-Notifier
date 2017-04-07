package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionStatus {
    public static final String CHECKEDOUTFORPAYMENT = "CheckedOutForPayment";
    public static final String EXPIRED = "Expired";
    public static final String PENDING = "Pending";
    public static final String PRESALE = "presale";
    public static final String RESERVATION = "reservation";
    @JsonProperty
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccessFul() {
        return PRESALE.equals(this.status) || RESERVATION.equals(this.status);
    }

    public boolean isInProgress() {
        return CHECKEDOUTFORPAYMENT.equals(this.status) || PENDING.equals(this.status);
    }

    public boolean isExpired() {
        return EXPIRED.equals(this.status);
    }
}
