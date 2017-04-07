package it.sijmen.movienotifier.pathe.dto;

import java.util.ArrayList;
import java.util.Iterator;

public class CinemaList extends ArrayList<Cinema> {
    private static final long serialVersionUID = 3671879418969962475L;

    public Cinema getCinemaById(long cinemaId) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Cinema c = (Cinema) it.next();
            if (c.getId() == cinemaId) {
                return c;
            }
        }
        return null;
    }
}
