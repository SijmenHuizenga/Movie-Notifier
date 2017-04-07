package it.sijmen.movienotifier.pathe.dto.seatselection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Seat implements Serializable {

    public static int STATE_AVAILABLE = 0;
    public static int STATE_DISABLED = 0;
    public static int STATE_RESERVED = 0;
    public static int STATE_RESERVED_BY_USER = 0;

    private static final long serialVersionUID = 7580069666933964127L;
    private Block block;
    private int index;
    private Row row;

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private int state;

    @JsonProperty
    private String type;

    @JsonProperty
    private int f301x;

    @JsonProperty
    private int f302y;

    static {
        STATE_AVAILABLE = 0;
        STATE_RESERVED = 1;
        STATE_RESERVED_BY_USER = 2;
        STATE_DISABLED = 3;
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

    public int getX() {
        return this.f301x;
    }

    public void setX(int x) {
        this.f301x = x;
    }

    public int getY() {
        return this.f302y;
    }

    public void setY(int y) {
        this.f302y = y;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Row getRow() {
        return this.row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
