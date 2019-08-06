package it.sijmen.movienotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FilterOption {
  @JsonProperty("yes")
  YES,

  @JsonProperty("no")
  NO,

  @JsonProperty("no-preference")
  NOPREFERENCE;

  @Override
  public String toString() {
    return this == YES ? "yes" : (this == NO ? "no" : "no-preference");
  }
}
