package it.sijmen.movienotifier.notifiers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.Message;
import com.restfb.types.send.PhoneMessageRecipient;
import com.restfb.types.send.SendResponse;
import it.sijmen.movienotifier.str.Recipient;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class FBMessengerNotifier extends Notifier {

    private FacebookClient pageClient;

    @Inject
    public FBMessengerNotifier(
            @Named("fb-messenger-token") String apiToken) {
        pageClient = new DefaultFacebookClient(apiToken, Version.VERSION_2_6);
    }

    @Override
    public void notify(Recipient recipient, String message) throws NotificationException {
        PhoneMessageRecipient phoneReceiver = new PhoneMessageRecipient(recipient.getPhonenumber());
        Message simpleTextMessage = new Message(message);

        SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient", phoneReceiver),
                Parameter.with("message", simpleTextMessage));

        if(!resp.isSuccessful())
            throw new NotificationException("Facebook Messagner responded with error. Result: " + resp.getResult());
    }
}
