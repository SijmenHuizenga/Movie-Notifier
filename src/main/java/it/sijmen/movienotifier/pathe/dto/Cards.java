package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Cards implements Serializable {

    private static final long serialVersionUID = -1997564664592938496L;

    @JsonProperty
    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

}
