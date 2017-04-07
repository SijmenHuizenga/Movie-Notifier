package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sijmen.movienotifier.pathe.dto.QuickSearchItem.ResultType;

import java.util.ArrayList;
import java.util.Iterator;

public class QuickSearchResult {

    private ArrayList<QuickSearchItem> all;

    @JsonProperty
    private ArrayList<QuickSearchItem> archived;
    @JsonProperty
    private ArrayList<QuickSearchItem> cast;
    @JsonProperty
    private ArrayList<QuickSearchItem> comingSoon;
    @JsonProperty
    private ArrayList<QuickSearchItem> faq;
    @JsonProperty
    private ArrayList<QuickSearchItem> nowPlaying;
    @JsonProperty("vod")
    private ArrayList<QuickSearchItem> onDemand;

    public ArrayList<QuickSearchItem> getAll() {
        if (this.all == null) {
            this.all = new ArrayList<>();
            addAll("Nu in de bioscoop", this.nowPlaying, ResultType.MOVIE);
            addAll("Verwacht", this.comingSoon, ResultType.MOVIE);
            addAll("Acteurs", this.cast, ResultType.CAST);
            addAll("Path\u00e9 thuis", this.onDemand, ResultType.MOVIE);
            addAll("Archief", this.archived, ResultType.MOVIE);
        }
        return this.all;
    }

    private void addAll(String heading, ArrayList<QuickSearchItem> list, ResultType type) {
        if (list.size() != 0) {
            this.all.add(new QuickSearchHeading(heading, list.size()));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                QuickSearchItem item = (QuickSearchItem) it.next();
                item.setResultType(type);
                this.all.add(item);
            }
        }
    }

    public ArrayList<QuickSearchItem> getNowPlaying() {
        return this.nowPlaying;
    }

    public void setNowPlaying(ArrayList<QuickSearchItem> nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    public ArrayList<QuickSearchItem> getComingSoon() {
        return this.comingSoon;
    }

    public void setComingSoon(ArrayList<QuickSearchItem> comingSoon) {
        this.comingSoon = comingSoon;
    }

    public ArrayList<QuickSearchItem> getArchived() {
        return this.archived;
    }

    public void setArchived(ArrayList<QuickSearchItem> archived) {
        this.archived = archived;
    }

    public ArrayList<QuickSearchItem> getCast() {
        return this.cast;
    }

    public void setCast(ArrayList<QuickSearchItem> cast) {
        this.cast = cast;
    }

    public ArrayList<QuickSearchItem> getOnDemand() {
        return this.onDemand;
    }

    public void setOnDemand(ArrayList<QuickSearchItem> onDemand) {
        this.onDemand = onDemand;
    }

    public ArrayList<QuickSearchItem> getFaq() {
        return this.faq;
    }

    public void setFaq(ArrayList<QuickSearchItem> faq) {
        this.faq = faq;
    }
}
