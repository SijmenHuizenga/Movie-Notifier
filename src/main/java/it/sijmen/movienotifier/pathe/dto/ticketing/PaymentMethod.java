package it.sijmen.movienotifier.pathe.dto.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = -1474347346615749354L;

    @JsonProperty
    private String code;

    @JsonProperty
    private int dolphinId;

    @JsonProperty
    private String image;

    @JsonProperty
    private int seqno;

    @JsonProperty
    private String title;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSeqno() {
        return this.seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getDolphinId() {
        return this.dolphinId;
    }

    public void setDolphinId(int dolphinId) {
        this.dolphinId = dolphinId;
    }
}
