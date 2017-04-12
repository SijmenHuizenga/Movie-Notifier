package it.sijmen.movienotifier;

import com.rollbar.Rollbar;
import it.sijmen.movienotifier.notifiers.FBMessengerNotifier;
import it.sijmen.movienotifier.notifiers.NotificationException;
import it.sijmen.movienotifier.str.Recipient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;

/**
 * Created by Sijmen on 12-4-2017.
 */
@Singleton
public class ErrorLogger {

    @Inject
    private FBMessengerNotifier notifier;

    @Inject
    @Named("administrator")
    private Recipient admin;

    private final Rollbar rollbar;

    @Inject
    public ErrorLogger(@Named("rollbar-token")String rollbartoken) {
        rollbar = new Rollbar(rollbartoken, "production");
    }

    public void log(String msg, Exception e){
        e.printStackTrace();
        try {
            notifier.notify(admin, msg +" "+ e);
        } catch (NotificationException e1) {
            HashMap<String, Object> errorMap = new HashMap<>();
            errorMap.put("Original error", e);
            errorMap.put("Admin Notificaiotn Error", e1);
            rollbar.error("Could send the following error to the administrator", errorMap);
        }
    }

}
