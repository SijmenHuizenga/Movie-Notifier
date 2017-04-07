package it.sijmen.movienotifier.pathe.dto;

import java.io.Serializable;
import java.util.*;

public class PatheCinemaSchedule implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    public final String TAG;
    private long mCinemaId;
    private Calendar mDisplayDate;
    private Calendar mFirstSchedule;
    private Calendar mLastSchedule;
    private ArrayList<Movie> movieScheduleList;


    public class SortByFirstShowComparator implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return ((PatheSchedule) o1.getScheduleList().get(0)).getStartOfShow().compareTo(((PatheSchedule) o2.getScheduleList().get(0)).getStartOfShow());
        }
    }

    public class SortByMostVisitedComparator implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            if (o1.getVisitors() < o2.getVisitors()) {
                return 1;
            }
            if (o1.getVisitors() == o2.getVisitors()) {
                return 0;
            }
            return -1;
        }
    }

    public class SortByNameComparator implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public class SortByRatingComparator implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            if (o1.getRating() < o2.getRating()) {
                return 1;
            }
            if (o1.getRating() == o2.getRating()) {
                return 0;
            }
            return -1;
        }
    }

    public class SortByReleaseDateComparator implements Comparator<Movie> {
        public int compare(Movie o1, Movie o2) {
            return o2.getReleaseDate().compareTo(o1.getReleaseDate());
        }
    }

    public PatheCinemaSchedule() {
        this.TAG = getClass().getSimpleName();
        this.movieScheduleList = new ArrayList();
        this.mFirstSchedule = Calendar.getInstance();
        this.mLastSchedule = Calendar.getInstance();
        this.mDisplayDate = Calendar.getInstance();
    }

    public long getmCinemaId() {
        return this.mCinemaId;
    }

    public void setmCinemaId(long mCinemaId) {
        this.mCinemaId = mCinemaId;
    }

    public ArrayList<Movie> getMovieScheduleList() {
        return this.movieScheduleList;
    }


    public void setFirstSchedule(Calendar earliestSchedule) {
        this.mFirstSchedule = earliestSchedule;
    }

    public void setLastSchedule(Calendar latestSchedule) {
        this.mLastSchedule = latestSchedule;
    }

    public void setDisplayDate(Calendar displayDate) {
        this.mDisplayDate = displayDate;
    }

    public Calendar getFirstSchedule() {
        return this.mFirstSchedule;
    }

    public Calendar getLastSchedule() {
        return this.mLastSchedule;
    }

    public Calendar getDisplayDate() {
        return this.mDisplayDate;
    }

    public void sortByFirstShow() {
        Collections.sort(this.movieScheduleList, new SortByFirstShowComparator());
    }

    public void sortByMostVisited() {
        Collections.sort(this.movieScheduleList, new SortByMostVisitedComparator());
    }

    public void sortByReleaseDate() {
        Collections.sort(this.movieScheduleList, new SortByReleaseDateComparator());
    }

    public void sortByRating() {
        Collections.sort(this.movieScheduleList, new SortByRatingComparator());
    }

    public void sortByName() {
        Collections.sort(this.movieScheduleList, new SortByNameComparator());
    }

    public int describeContents() {
        return 0;
    }

}
