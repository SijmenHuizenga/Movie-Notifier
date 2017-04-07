package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PatheKeyValue implements Serializable {
    private static final long serialVersionUID = 505499559222053414L;
    @JsonProperty
    private long id;
    @JsonProperty
    private String key;
    @JsonProperty
    private int seqno;
    @JsonProperty
    private String title;
    @JsonProperty
    private String value;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSeqno() {
        return this.seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
