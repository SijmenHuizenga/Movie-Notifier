package it.sijmen.movienotifier.pathe.dto.ticketing;

import it.sijmen.movienotifier.pathe.dto.Movie;
import it.sijmen.movienotifier.pathe.dto.PatheSchedule;
import it.sijmen.movienotifier.pathe.dto.TicketTypeAndAmount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentState implements Serializable {

    private static final long serialVersionUID = 5156442647424590182L;
    private Movie mMovie;
    private PatheSchedule mSchedule;
    private HashMap<String, List<DiscountCard>> mSelectedDiscounts;
    private TicketsForSchedule mTicketsForSchedule;
    private Transaction mTransaction;
    private PaymentMethodList paymentMethods;
    private int selectedTicketCategory;
    private long ticketTypeIdForBarcode;
    private int ticketTypeIdxForBarcode;
    private List<TicketTypeAndAmount> ticketTypesAndAmounts;

    public static class DiscountCard implements Serializable {
        private static final long serialVersionUID = -7506416005633080861L;
        private String code;
        private String pin;
        private boolean saved;
        private boolean validated;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPin() {
            return this.pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public boolean isSaved() {
            return this.saved;
        }

        public void setSaved(boolean saved) {
            this.saved = saved;
        }

        public boolean isValidated() {
            return this.validated;
        }

        public void setValidated(boolean validated) {
            this.validated = validated;
        }

        public void setSavedCode(String code) {
            this.code = code;
            this.saved = true;
        }
    }

    public PaymentState() {
        this.mSelectedDiscounts = new HashMap();
    }

    public Transaction getTransaction() {
        return this.mTransaction;
    }

    public void setTransaction(Transaction mTransaction) {
        this.mTransaction = mTransaction;
    }

    public TicketsForSchedule getTicketsForSchedule() {
        return this.mTicketsForSchedule;
    }

    public void setTicketsForSchedule(TicketsForSchedule mTicketsForSchedule) {
        this.mTicketsForSchedule = mTicketsForSchedule;
    }

    public HashMap<String, List<DiscountCard>> getSelectedDiscounts() {
        return this.mSelectedDiscounts;
    }

    public void setSelectedDiscounts(HashMap<String, List<DiscountCard>> mSelectedDiscounts) {
        this.mSelectedDiscounts = mSelectedDiscounts;
    }

    public PatheSchedule getSchedule() {
        return this.mSchedule;
    }

    public void setSchedule(PatheSchedule mSchedule) {
        this.mSchedule = mSchedule;
    }

    public PaymentMethodList getPaymentMethods() {
        return this.paymentMethods;
    }

    public void setPaymentMethods(PaymentMethodList paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Movie getMovie() {
        return this.mMovie;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }

    public void clearTicketTypesAndAmounts() {
        this.ticketTypesAndAmounts = new ArrayList();
    }

    public List<TicketTypeAndAmount> getTicketTypesAndAmounts() {
        return this.ticketTypesAndAmounts;
    }

    public void setTicketTypesAndAmounts(List<TicketTypeAndAmount> ticketTypesAndAmounts) {
        this.ticketTypesAndAmounts = ticketTypesAndAmounts;
    }

    public long getTicketTypeIdForBarcode() {
        return this.ticketTypeIdForBarcode;
    }

    public void setTicketTypeIdForBarcode(long ticketTypeIdForBarcode) {
        this.ticketTypeIdForBarcode = ticketTypeIdForBarcode;
    }

    public int getTicketTypeIdxForBarcode() {
        return this.ticketTypeIdxForBarcode;
    }

    public void setTicketTypeIdxForBarcode(int ticketTypeIdxForBarcode) {
        this.ticketTypeIdxForBarcode = ticketTypeIdxForBarcode;
    }

    public int getSelectedTicketCategory() {
        return this.selectedTicketCategory;
    }

    public void setSelectedTicketCategory(int selectedTicketCategory) {
        this.selectedTicketCategory = selectedTicketCategory;
    }

}
