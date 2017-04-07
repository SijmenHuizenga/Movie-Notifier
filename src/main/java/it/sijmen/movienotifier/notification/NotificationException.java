package it.sijmen.movienotifier.notification;

import java.io.IOException;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class NotificationException extends IOException {

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
