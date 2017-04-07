package it.sijmen.movienotifier.pathe.dto;

public class QuickSearchHeading extends QuickSearchItem {
    private int count;

    public QuickSearchHeading(String name, int count) {
        setCount(count);
        setName(name);
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
