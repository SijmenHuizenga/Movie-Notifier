package it.sijmen.movienotifier.integrationtests;

import java.util.Map;

public class HashMapEqualizer {

    public static boolean equals(Map a, Map b) {
        return a.size() == b.size() && checkOneWay(a, b) && checkOneWay(b, a);
    }

    private static boolean checkOneWay(Map a, Map b){
        for(Object aKey : a.keySet()){
            if(!b.containsKey(aKey))
                return false;
            if(a.get(aKey) instanceof Map){
                if(!(b.get(aKey) instanceof Map))
                    return false;
                if(!equals((Map)a.get(aKey), (Map)b.get(aKey)))
                    return false;
            } else if(a.get(aKey) instanceof Number){
                if(!(b.get(aKey) instanceof Number))
                    return false;
                if(((Number) a.get(aKey)).longValue() != ((Number) b.get(aKey)).longValue())
                    return false;
            } else if(!a.get(aKey).equals(b.get(aKey)))
                return false;
        }
        return true;
    }

}
