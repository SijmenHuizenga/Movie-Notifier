package it.sijmen.movienotifier.pathe.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatheKeyValueMap extends ArrayList<PatheKeyValue> {

    private static final long serialVersionUID = 1;
    private Map<String, PatheKeyValue> mByKey;

    public PatheKeyValueMap() {
        this.mByKey = new HashMap();
    }

    public boolean add(PatheKeyValue object) {
        this.mByKey.put(object.getKey(), object);
        return super.add(object);
    }

    public void add(int index, PatheKeyValue object) {
        this.mByKey.put(object.getKey(), object);
        super.add(index, object);
    }

    public PatheKeyValue remove(int index) {
        this.mByKey.remove(((PatheKeyValue) get(index)).getKey());
        return (PatheKeyValue) super.remove(index);
    }

    public boolean remove(Object object) {
        this.mByKey.remove(((PatheKeyValue) object).getKey());
        return super.remove(object);
    }

    public boolean containsKey(String key) {
        return this.mByKey.containsKey(key);
    }

    public PatheKeyValue getByKey(String key) {
        return (PatheKeyValue) this.mByKey.get(key);
    }
}
