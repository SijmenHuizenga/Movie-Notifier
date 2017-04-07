package it.sijmen.movienotifier.notification;

/**
 * Created by Sijmen on 7-4-2017.
 */
public abstract class Notifier {

    public abstract void notify(Recipient recipient, String message) throws NotificationException;

}
