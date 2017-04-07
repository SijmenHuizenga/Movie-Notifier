package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StartPayment implements Serializable {

    private static final long serialVersionUID = 1;

    @JsonProperty
    private String paymentUrl;

    @JsonProperty
    private int totalAmount;

    @JsonProperty
    private int transactionFee;

    @JsonProperty
    private long transactionId;

    @JsonProperty
    private int unpaidAmount;

    public long getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUnpaidAmount() {
        return this.unpaidAmount;
    }

    public void setUnpaidAmount(int unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public int getTransactionFee() {
        return this.transactionFee;
    }

    public void setTransactionFee(int transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getPaymentUrl() {
        return this.paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
