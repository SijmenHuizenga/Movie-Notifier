package it.sijmen.movienotifier.pathe.dto;

public class PatheKijkwijzer {
    public static final int ALL = 63;
    public static final int DISCRIMINATION = 16;
    public static final int DRUGS = 32;
    public static final int FEAR = 1;
    public static final int LANGUAGE = 8;
    public static final int SET = 1;
    public static final int SEX = 2;
    public static final int UNSET = 0;
    public static final int VIOLENCE = 4;
    public int bitMaskValue;

    public PatheKijkwijzer() {
        this.bitMaskValue = UNSET;
    }

    public boolean has(int kijkwijzerMask) {
        return (this.bitMaskValue & kijkwijzerMask) == kijkwijzerMask;
    }

    public boolean hasFear() {
        return (this.bitMaskValue & SET) == SET;
    }

    public boolean hasSex() {
        return (this.bitMaskValue & SEX) == SEX;
    }

    public boolean hasViolence() {
        return (this.bitMaskValue & VIOLENCE) == VIOLENCE;
    }

    public boolean hasLanguage() {
        return (this.bitMaskValue & LANGUAGE) == LANGUAGE;
    }

    public boolean hasDiscrimination() {
        return (this.bitMaskValue & DISCRIMINATION) == DISCRIMINATION;
    }

    public boolean hasDrugs() {
        return (this.bitMaskValue & DRUGS) == DRUGS;
    }

    public void setAll(int selector) {
        setFear(selector);
        setDrugs(selector);
        setSex(selector);
        setViol(selector);
        setDisc(selector);
        setLang(selector);
    }

    public void setFear(int fear) {
        if (fear > 0) {
            this.bitMaskValue |= SET;
        } else {
            this.bitMaskValue &= -2;
        }
    }

    public void setSex(int sex) {
        if (sex > 0) {
            this.bitMaskValue |= SEX;
        } else {
            this.bitMaskValue &= -3;
        }
    }

    public void setViol(int viol) {
        if (viol > 0) {
            this.bitMaskValue |= VIOLENCE;
        } else {
            this.bitMaskValue &= -5;
        }
    }

    public void setLang(int lang) {
        if (lang > 0) {
            this.bitMaskValue |= LANGUAGE;
        } else {
            this.bitMaskValue &= -9;
        }
    }

    public void setDisc(int disc) {
        if (disc > 0) {
            this.bitMaskValue |= DISCRIMINATION;
        } else {
            this.bitMaskValue &= -17;
        }
    }

    public void setDrugs(int drugs) {
        if (drugs > 0) {
            this.bitMaskValue |= DRUGS;
        } else {
            this.bitMaskValue &= -33;
        }
    }

    public void setMask(int kijkwijzerMask) {
        this.bitMaskValue = kijkwijzerMask;
    }

    public int getMask() {
        return this.bitMaskValue;
    }
}
