package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.pathe.dto.ticketing.Ticket.Seated;

import java.io.Serializable;
import java.util.*;

public class Transaction implements Serializable {

    public static final transient String STATE_BOOKED = "Booked";
    public static final transient String STATE_CANCELLED = "Cancelled";
    public static final transient String STATE_RESERVED = "Reserved";

    public static final String STUB_ROW = "STUB_ROW";

    private static final long serialVersionUID = 4206296614898723222L;

    @JsonProperty
    private String barcode;
    @JsonProperty
    private int checkoutType;
    @JsonProperty
    private long cinemaId;
    @JsonProperty
    private String cinemaName;
    @JsonProperty
    private ArrayList<Extra> extras;
    @JsonProperty
    private ArrayList<GroupedExtra> groupedExtras;
    @JsonProperty
    protected long id;
    @JsonProperty
    private String languageVersion;
    @JsonProperty("_migrationSeatDetails")
    private String migrationSeatDetails;
    @JsonProperty
    private long movieId;
    @JsonProperty
    private String movieName;
    @JsonProperty
    private String paymentMethods;
    @JsonProperty
    private String pdfUrl;
    @JsonProperty
    private String reservationNumber;
    @JsonProperty
    private String screenName;
    @JsonProperty
    private boolean seated;
    @JsonProperty
    private String seatingCategory;
    @JsonProperty
    private ArrayList<Seated> seats;
    @JsonProperty
    private Calendar showEnd;
    @JsonProperty
    private Calendar showTime;
    @JsonProperty
    private long specialId;
    @JsonProperty
    private String specialName;
    @JsonProperty
    protected String state;
    @JsonProperty
    private String thumb;
    @JsonProperty
    private ArrayList<Ticket> tickets;
    @JsonProperty
    private int totalAmount;
    @JsonProperty
    private int transactionFee;
    @JsonProperty
    protected String transactionStatus;
    @JsonProperty
    private int unpaidAmount;

    public static class List implements Serializable {
        private static final long serialVersionUID = -3302517118888606034L;
        @JsonProperty
        private ArrayList<Transaction> transactions;

        public ArrayList<Transaction> getTransactions() {
            return this.transactions;
        }

        public void setTransactions(ArrayList<Transaction> transactions) {
            this.transactions = transactions;
        }

        public void add(Transaction transaction) {
            if (this.transactions == null) {
                this.transactions = new ArrayList();
            }
            this.transactions.add(transaction);
        }
    }

    public static class OrderLine {
        public int amount;
        public String name;
        public int price;

        public OrderLine() {
            this.amount = 0;
            this.price = 0;
        }
    }

    public Transaction postProcess() {
        this.groupedExtras = new ArrayList();
        if (this.extras != null) {
            Iterator it = this.extras.iterator();
            while (it.hasNext()) {
                Extra e = (Extra) it.next();
                GroupedExtra currentGroup = null;
                Iterator it2 = this.groupedExtras.iterator();
                while (it2.hasNext()) {
                    GroupedExtra g = (GroupedExtra) it2.next();
                    if (g.getName().equals(e.getName())) {
                        currentGroup = g;
                        currentGroup.addExtra(e);
                        break;
                    }
                }
                if (currentGroup == null) {
                    this.groupedExtras.add(new GroupedExtra(e));
                }
            }
        }
        return this;
    }

    public void clearExtras() {
        this.extras = new ArrayList<>();
        postProcess();
    }

    public void clearTickets() {
        this.tickets = new ArrayList<>();
        postProcess();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
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

    public ArrayList<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public boolean isSeated() {
        return this.seated;
    }

    public void setSeated(boolean seated) {
        this.seated = seated;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getCinemaId() {
        return this.cinemaId;
    }

    public void setCinemaId(long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return this.cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getLanguageVersion() {
        return this.languageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        this.languageVersion = languageVersion;
    }

    public long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReservationNumber() {
        return this.reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Calendar getShowTime() {
        return this.showTime;
    }

    public void setShowTime(Calendar showTime) {
        this.showTime = showTime;
    }

    public long getSpecialId() {
        return this.specialId;
    }

    public void setSpecialId(long specialId) {
        this.specialId = specialId;
    }

    public String getSpecialName() {
        return this.specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public ArrayList<GroupedExtra> getGroupedExtras() {
        return this.groupedExtras;
    }

    public void setGroupedExtras(ArrayList<GroupedExtra> groupedExtras) {
        this.groupedExtras = groupedExtras;
    }

    public int getCheckoutType() {
        return this.checkoutType;
    }

    public void setCheckoutType(int checkoutType) {
        this.checkoutType = checkoutType;
    }

    public boolean isCheckoutPossible() {
        return this.checkoutType == 1 || this.checkoutType == 2 || this.checkoutType == 4 || this.checkoutType == 6;
    }

    public boolean isPaymentNeeded() {
        return this.unpaidAmount > 0;
    }

    public String getMigrationSeatDetails() {
        return this.migrationSeatDetails;
    }

    public void setMigrationSeatDetails(String migrationSeatDetails) {
        this.migrationSeatDetails = migrationSeatDetails;
    }

    public boolean isMigrated() {
        return this.migrationSeatDetails != null && this.movieId == 0;
    }

    public String getPdfUrl() {
        return this.pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public int getSeatCount() {
        int result = 0;
        Iterator it = this.tickets.iterator();
        while (it.hasNext()) {
            result += ((Ticket) it.next()).getSeats().size();
        }
        return result;
    }

    public Calendar getShowEnd() {
        return this.showEnd;
    }

    public void setShowEnd(Calendar showEnd) {
        this.showEnd = showEnd;
    }

    public boolean isExpired() {
        return getShowTime().before(Calendar.getInstance());
    }

    public String getPaymentMethods() {
        return this.paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getSeatingCategory() {
        return this.seatingCategory;
    }

    public void setSeatingCategory(String seatingCategory) {
        this.seatingCategory = seatingCategory;
    }
}
