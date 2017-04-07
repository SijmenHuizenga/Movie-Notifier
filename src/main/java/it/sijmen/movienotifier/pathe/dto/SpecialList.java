package it.sijmen.movienotifier.pathe.dto;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecialList extends ArrayList<Special> {
    private static final long serialVersionUID = 3310570291025975414L;

    public ArrayList<Special> getByType(String type) {
        ArrayList<Special> result = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            Special s = (Special) it.next();
            if (s.getType().equals(type)) {
                result.add(s);
            }
        }
        return result;
    }

    public Special getById(long id) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Special s = (Special) it.next();
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
}
