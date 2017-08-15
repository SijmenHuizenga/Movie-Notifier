package it.sijmen.movienotifier.service.notification.notifiers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.Message;
import com.restfb.types.send.PhoneMessageRecipient;
import com.restfb.types.send.SendResponse;
import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Component
public class FBMessengerNotifier implements Notifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(FBMessengerNotifier.class);

    private FacebookClient pageClient;

    public static final String ID = "FBM";

    @Inject
    public FBMessengerNotifier(
            @Value("${notification.facebook.token}") String apiToken) {
        pageClient = new DefaultFacebookClient(apiToken, Version.VERSION_2_6);
    }

    @Override
    public void notify(User recipient, String message) throws IOException {
        PhoneMessageRecipient phoneReceiver = new PhoneMessageRecipient(recipient.getPhonenumber());
        Message simpleTextMessage = new Message(message);

        SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient", phoneReceiver),
                Parameter.with("message", simpleTextMessage));

        if(!resp.isSuccessful())
            throw new IOException("Facebook Messenger responded with error. Result: " + resp.getResult());
        LOGGER.info("Sent FB Messenger notification to %s. Message: %s", phoneReceiver, simpleTextMessage);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Facebook Messenger";
    }

    @Override
    public String getDescription() {
        return "Get a message through facebook messenger";
    }
}
