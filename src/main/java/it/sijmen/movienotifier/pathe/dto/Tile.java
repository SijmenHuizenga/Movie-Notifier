package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Tile implements Serializable {

    private static final long serialVersionUID = 2160755412705869601L;

    private int badge;
    @JsonProperty
    private String bannerImage;
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean openInNewWindow;
    @JsonProperty
    private int seqno;
    @JsonProperty
    private String statsUrl;
    @JsonProperty
    private String subTitle;
    @JsonProperty
    private String title;
    @JsonProperty
    private String url;

    public Tile() {
        this.badge = 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeqno() {
        return this.seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOpenInNewWindow() {
        return this.openInNewWindow;
    }

    public void setOpenInNewWindow(boolean openInNewWindow) {
        this.openInNewWindow = openInNewWindow;
    }

    public String getPosterUrl() {
        return this.bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int describeContents() {
        return 0;
    }

    public String getStatsUrl() {
        return this.statsUrl;
    }

    public void setStatsUrl(String statsUrl) {
        this.statsUrl = statsUrl;
    }

    public int getBadge() {
        return this.badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }
}
