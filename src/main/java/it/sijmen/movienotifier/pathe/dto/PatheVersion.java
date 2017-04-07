package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PatheVersion implements Serializable {

    public static final boolean SET = true;
    public static final boolean UN_SET = false;

    public static final String _3D_STRING = "3D";
    public static final String _IMAX_STRING = "IMAX";
    public static final String _OV_STRING = "OV";
    public static final String _NL_STRING = "NL";
    public static final String _DGTL_STRING = "DGTL";
    public static final int _3D = 1;

    public static final int _IMAX = 2;
    public static final int _OV = 4;
    public static final int _NL = 8;
    public static final int _DGTL = 16;
    public static final int _ALL = 31;

    private static final long serialVersionUID = 3085029919063822127L;

    private int bitMaskValue;

    @JsonProperty
    private long id;
    @JsonProperty
    private boolean is2d;
    @JsonProperty
    private boolean is3d;
    @JsonProperty
    private boolean isDigital;
    @JsonProperty
    private boolean isDutch;
    @JsonProperty
    private boolean isImax;
    @JsonProperty
    private boolean isOriginal;
    @JsonProperty
    private String name;

    public PatheVersion() {
        this.bitMaskValue = 0;
        this.is2d = SET;
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

    public boolean is3d() {
        return this.is3d;
    }

    public void setIs3d(boolean is3d) {
        this.is3d = is3d;
    }

    public boolean isImax() {
        return this.isImax;
    }

    public void setImax(boolean isImax) {
        this.isImax = isImax;
    }

    public boolean isDutch() {
        return this.isDutch;
    }

    public void setDutch(boolean isDutch) {
        this.isDutch = isDutch;
    }

    public boolean isOriginal() {
        return this.isOriginal;
    }

    public void setOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public boolean isDigital() {
        return this.isDigital;
    }

    public void setDigital(boolean isDigital) {
        this.isDigital = isDigital;
    }

    public boolean has(int patheVersionMask) {
        switch (patheVersionMask) {
            case _3D /*1*/:
                return this.is3d;
            case _IMAX /*2*/:
                return this.isImax;
            case _OV /*4*/:
                return this.isOriginal;
            case _NL /*8*/:
                return this.isDutch;
            case _DGTL /*16*/:
                return this.isDigital;
            default:
                return UN_SET;
        }
    }

    public static boolean has(int patheVersionMask, int selectedMask) {
        return (patheVersionMask & selectedMask) == selectedMask ? SET : UN_SET;
    }

    public void setAll(boolean selector) {
        this.is3d = selector;
        this.isDigital = selector;
        this.isDutch = selector;
        this.isImax = selector;
        this.isOriginal = selector;
    }

    public void setMask(int movieVersionMask) {
        this.bitMaskValue = movieVersionMask;
    }

    public int getMask() {
        return this.bitMaskValue;
    }

    public boolean is2d() {
        return this.is2d;
    }

    public void setIs2d(boolean is2d) {
        this.is2d = is2d;
    }

    public static PatheVersion getDefaultFilter() {
        PatheVersion filterVersion = new PatheVersion();
        filterVersion.setAll(SET);
        filterVersion.is2d = SET;
        return filterVersion;
    }

    public String toString() {
        String s = "";
        if (has(_OV)) {
            s = s + "OV ";
        }
        if (has(_NL)) {
            s = s + "NL ";
        }
        if (has(_3D)) {
            s = s + "3D ";
        }
        if (has(_IMAX)) {
            s = s + "IMAX ";
        }
        if (has(_DGTL)) {
            return s + "Digital ";
        }
        return s;
    }
}
