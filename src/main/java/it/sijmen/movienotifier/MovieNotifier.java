package it.sijmen.movienotifier;

import it.sijmen.movienotifier.notifiers.*;
import it.sijmen.movienotifier.str.Recipient;

import javax.inject.Inject;

/**
 * Hello world!
 */
public class MovieNotifier {

    @Inject
    private FBMessengerNotifier fbMessengerNotifier;

    @Inject
    private MailNotifier mailNotifier;

    @Inject
    private SMSNotifier smsNotifier;

    private Notifier[] getNotifiers(){
        return new Notifier[]{
                fbMessengerNotifier, mailNotifier, smsNotifier
        };
    }

    public void run() {
        //todo: load recipients dynamicly
        Recipient recipient = new Recipient("", "", "");
        for (Notifier notifier : getNotifiers()) {
            try {
                notifier.notify(recipient, "Public Test Message");
            } catch (NotificationException e) {
                e.printStackTrace();
            }
        }
    }
}
