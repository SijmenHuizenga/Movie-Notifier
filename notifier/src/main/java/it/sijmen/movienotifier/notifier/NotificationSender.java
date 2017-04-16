package it.sijmen.movienotifier.notifier;

import com.google.inject.Inject;
import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import it.sijmen.movienotifier.notifier.notifiers.Notifier;
import spark.HaltException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * Created by Sijmen on 16-4-2017.
 */
class NotificationSender {

    private Notifier smsNotifier;

    private Notifier fbMessengerNotifier;

    private Notifier mailNotifier;

    @Inject
    NotificationSender(
            @Notifier.FBMSG Notifier smsNotifier,
            @Notifier.FBMSG Notifier fbMessengerNotifier,
            @Notifier.MAIL Notifier mailNotifier) {
        this.smsNotifier = smsNotifier;
        this.fbMessengerNotifier = fbMessengerNotifier;
        this.mailNotifier = mailNotifier;
    }

    void sendNotifaction(Recipient recipient, RecipientSettings settings, String message) throws HaltException {
        if(recipient == null)
            throw new IllegalArgumentException("recipient cannot be null.");
        if(settings == null)
            throw new IllegalArgumentException("settings cannot be null.");
        if(message == null || message.isEmpty())
            throw new IllegalArgumentException("message cannot be null.");

        List<String> errors = new ArrayList<>();
        if(settings.isReceiveEmails())
            errors.add(sendNotification(mailNotifier, recipient, message));
        if(settings.isReceiveFbMessge())
            errors.add(sendNotification(fbMessengerNotifier, recipient, message));
        if(settings.isReceiveSms())
            errors.add(sendNotification(smsNotifier, recipient, message));
        validateNotificationResult(errors);
    }

    private void validateNotificationResult(List<String> notificationResults) throws HaltException {
        int totalSent = notificationResults.size();
        notificationResults.removeIf(Objects::isNull);
        int errrosSent = notificationResults.size();
        int succesfullSent = totalSent - errrosSent;

        if(totalSent == 0)
            throw halt(400, "Bad Request. The recipientsettings has not selected any notifications.");
        if(errrosSent > 0){
            if(succesfullSent > 0)
                throw halt(202, "The notification of the recipient was partially succesfull. " +
                        "At least 1 notification method is successfull and at least 1 notification method has failed. " +
                        "Details:\n"+String.join("\n", notificationResults));
            else
                throw halt(501, "Internal notification error. None of the notifications have been sent. " +
                        "Details:\n"+String.join("\n", notificationResults));
        }
    }

    private String sendNotification(Notifier notifier, Recipient recipient, String message) {
        try {
            notifier.notify(recipient, message);
            return null;
        } catch (Exception e) {
            return toString(e);
        }
    }

    private String toString(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

}
