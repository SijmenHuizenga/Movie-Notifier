package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

public class CinemasContainer implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    public final String TAG;

    @JsonProperty("cinemaList")
    private ArrayList<Cinema> mCinemaList;

    public static class SortByCinemaNameComparator implements Comparator<Cinema> {
        public int compare(Cinema o1, Cinema o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public static class SortByCityNameComparator implements Comparator<Cinema> {
        public int compare(Cinema o1, Cinema o2) {
            return o1.getCity().getName().compareToIgnoreCase(o2.getCity().getName());
        }
    }

    public CinemasContainer() {
        this.TAG = getClass().getSimpleName();
        this.mCinemaList = new ArrayList();
    }

    public CinemasContainer(ArrayList<Cinema> cinemaList) {
        this();
        setCinemaList(cinemaList);
    }

    public ArrayList<Cinema> getCinemaList() {
        return this.mCinemaList;
    }

    public void setCinemaList(ArrayList<Cinema> cinemaList) {
        this.mCinemaList = cinemaList;
        sortByCinemaName();
    }

    public int getSizeAllCinemas() {
        return this.mCinemaList.size();
    }

    public Cinema getCinema(int pos) {
        return (Cinema) this.mCinemaList.get(pos);
    }

    public Cinema getCinemaByCityId(long cinemaID) {
        Iterator it = this.mCinemaList.iterator();
        while (it.hasNext()) {
            Cinema cinema = (Cinema) it.next();
            if (cinema.getCity().getId() == cinemaID) {
                return cinema;
            }
        }
        return null;
    }

    public Cinema getCinemaById(long cinemaID) {
        Iterator it = this.mCinemaList.iterator();
        while (it.hasNext()) {
            Cinema cinema = (Cinema) it.next();
            if (cinema.getId() == cinemaID) {
                return cinema;
            }
        }
        return null;
    }

    public int getSizeAllCities() {
        return getCinemasPerCityList().size();
    }

    public ArrayList<ArrayList<Cinema>> getCinemasPerCityList() {
        ArrayList<Cinema> allCinemasByCity = getAllCinemasSortedByCity();
        ArrayList<ArrayList<Cinema>> newCityList = new ArrayList();
        Iterator it = allCinemasByCity.iterator();
        while (it.hasNext()) {
            Cinema cinema = (Cinema) it.next();
            ArrayList<Cinema> newCinemaList;
            if (newCityList.size() == 0) {
                newCinemaList = new ArrayList();
                newCinemaList.add(cinema);
                newCityList.add(newCinemaList);
            } else {
                Iterator it2 = newCityList.iterator();
                while (it2.hasNext()) {
                    ArrayList<Cinema> cinemaList = (ArrayList) it2.next();
                    if (((Cinema) cinemaList.get(0)).getCity().getId() == cinema.getCity().getId()) {
                        cinemaList.add(cinema);
                        break;
                    }
                }
                newCinemaList = new ArrayList();
                newCinemaList.add(cinema);
                newCityList.add(newCinemaList);
            }
        }
        return newCityList;
    }

    public ArrayList<Cinema> getAllCinemasSortedByCity() {
        ArrayList<Cinema> sortedByCityList = this.mCinemaList;
        Collections.sort(sortedByCityList, new SortByCityNameComparator());
        return sortedByCityList;
    }

    public List<Long> getCinemaIds(int n) {
        ArrayList<Long> result = new ArrayList(n);
        int i = 0;
        while (i < n && i < this.mCinemaList.size()) {
            result.add(Long.valueOf(((Cinema) this.mCinemaList.get(i)).getId()));
            i++;
        }
        return result;
    }

    public void sortByCityName() {
        Collections.sort(this.mCinemaList, new SortByCityNameComparator());
    }

    public void sortByCinemaName() {
        Collections.sort(this.mCinemaList, new SortByCinemaNameComparator());
    }

    public int describeContents() {
        return 0;
    }

}
