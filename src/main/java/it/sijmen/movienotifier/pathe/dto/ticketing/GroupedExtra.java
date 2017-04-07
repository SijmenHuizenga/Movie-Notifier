package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class GroupedExtra implements Serializable {

    private static final long serialVersionUID = 7591286439917235371L;

    @JsonProperty
    private ArrayList<Extra> extras;

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private int price;

    public GroupedExtra() {
        this.extras = new ArrayList();
    }

    public GroupedExtra(Extra e) {
        this.id = e.getId();
        this.name = e.getName();
        this.price = e.getPrice();
        this.extras = new ArrayList();
        addExtra(e);
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

    public ArrayList<Extra> getExtras() {
        return this.extras;
    }

    public void setExtras(ArrayList<Extra> extras) {
        this.extras = extras;
    }

    public int getMaxCount() {
        return this.extras.size();
    }

    public int getMinCount() {
        int nr = getMaxCount();
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            if (((Extra) it.next()).isOptional()) {
                nr--;
            }
        }
        return nr;
    }

    public int getCurrentCount() {
        int count = 0;
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            if (((Extra) it.next()).isAdded()) {
                count++;
            }
        }
        return count;
    }

    public long getTicketIdForAdd() {
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            Extra e = (Extra) it.next();
            if (!e.isAdded()) {
                return e.getTicketId();
            }
        }
        return -1;
    }

    public long getTicketIdForRemove() {
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            Extra e = (Extra) it.next();
            if (e.isAdded()) {
                return e.getTicketId();
            }
        }
        return -1;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addExtra(Extra e) {
        this.extras.add(e);
    }

    public long getExtraIdForRemove() {
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            Extra e = (Extra) it.next();
            if (e.isAdded()) {
                return e.getId();
            }
        }
        return -1;
    }

    public long getExtraIdForAdd() {
        Iterator it = this.extras.iterator();
        while (it.hasNext()) {
            Extra e = (Extra) it.next();
            if (!e.isAdded()) {
                return e.getId();
            }
        }
        return -1;
    }
}
