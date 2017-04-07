package it.sijmen.movienotifier.pathe.dto;

import java.util.ArrayList;
import java.util.Iterator;

public class MovieGenreList extends ArrayList<MovieGenre> {
    private static final long serialVersionUID = -6054810212695153577L;

    public MovieGenre getById(long id) {
        Iterator it = iterator();
        while (it.hasNext()) {
            MovieGenre g = (MovieGenre) it.next();
            if (g.getId() == id) {
                return g;
            }
        }
        return null;
    }
}
