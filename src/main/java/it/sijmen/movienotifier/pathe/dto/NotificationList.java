package it.sijmen.movienotifier.pathe.dto;

import java.util.*;

public class NotificationList extends ArrayList<Notification> {
    private static final long serialVersionUID = 2784570262121647412L;

    public Notification getById(long id) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Notification n = (Notification) it.next();
            if (id == n.getId()) {
                return n;
            }
        }
        return null;
    }

    public Notification getByCode(String code) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Notification n = (Notification) it.next();
            if (code.equals(n.getCode())) {
                return n;
            }
        }
        return null;
    }

    public boolean hasNotification(long id) {
        return getById(id) != null;
    }

    public boolean hasNotification(String code) {
        return getByCode(code) != null;
    }

    public SortedMap<String, List<Notification>> getGroups() {
        TreeMap<String, List<Notification>> result = new TreeMap();
        Iterator it = iterator();
        while (it.hasNext()) {
            Notification n = (Notification) it.next();
            String type = n.getType();
            if (!result.containsKey(type)) {
                result.put(type, new ArrayList());
            }
            ((List) result.get(type)).add(n);
        }
        return result;
    }
}
