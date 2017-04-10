package it.sijmen.movienotifier.str;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sijmen on 10-4-2017.
 */
@Singleton
public class NotifierConfiguration {

    //key = recipient.key
    private final HashMap<String, Recipient> recipients = new HashMap<>();

    //key = movieID
    private final HashMap<Integer, List<NewScheduleListener>> listeners = new HashMap<>();

    public void addListener(NewScheduleListener listener){
        if(!listener.isValid())
            throw new IllegalArgumentException("Listener not valid");
        if(!recipients.keySet().containsAll(listener.getRecipients()))
            throw new IllegalArgumentException("Not all recipiants are known");
        if(!listeners.containsKey(listener.getMovieid()))
            listeners.put(listener.getMovieid(), new ArrayList<>());
        listeners.get(listener.getMovieid()).add(listener);
    }

    public void addRecipient(Recipient recipient){
        if(recipient.isValid())
            throw new IllegalArgumentException("Recipient not valid");
        if(recipients.containsKey(recipient.getKey()))
            throw new IllegalArgumentException("Recipient already exists");
        recipients.put(recipient.getKey(), recipient);
    }

    public Recipient getRecipient(String key){
        return recipients.get(key);
    }

    public HashMap<Integer, List<NewScheduleListener>> getListeners() {
        return listeners;
    }
}
