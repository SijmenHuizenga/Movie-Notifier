package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PagedList<T> {
    @JsonProperty
    private int page;
    @JsonProperty
    private int pageSize;
    @JsonProperty
    private ArrayList<T> results;
    @JsonProperty
    private int total;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<T> getResults() {
        return this.results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }
}
