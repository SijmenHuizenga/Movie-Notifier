package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class TicketsForSchedule implements Serializable {

    private static final long serialVersionUID = 7270346039882298464L;

    @JsonProperty
    private List<TicketCategory> categories;
    @JsonProperty
    private String cinemaName;
    @JsonProperty
    private int maxTickets;
    @JsonProperty
    private String movieName;
    @JsonProperty
    private String pAge;
    @JsonProperty
    private int runtime;
    @JsonProperty
    private String screenName;
    @JsonProperty
    private Calendar showTime;
    @JsonProperty
    private int ticketsAvailable;

    public static class TicketCategory implements Serializable {
        private static final long serialVersionUID = -1251437196282803143L;
        @JsonProperty
        private String name;
        @JsonProperty
        private List<Ticket> tickets;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Ticket> getTickets() {
            return this.tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }
    }

    public int getTicketsAvailable() {
        return this.ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return this.cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
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

    public int getRuntime() {
        return this.runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getAge() {
        return this.pAge;
    }

    public void setAge(String pAge) {
        this.pAge = pAge;
    }

    public int getMaxTickets() {
        return Math.min(this.maxTickets, this.ticketsAvailable);
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public List<TicketCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(List<TicketCategory> categories) {
        this.categories = categories;
    }
}
