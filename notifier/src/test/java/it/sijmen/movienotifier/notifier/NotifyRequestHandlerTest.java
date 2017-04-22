package it.sijmen.movienotifier.notifier;

import com.google.inject.Provider;
import it.sijmen.movienotifier.coder.JsonCoder;
import it.sijmen.movienotifier.notifier.notifiers.Notifier;
import it.sijmen.movienotifier.notifier.requests.NotifyRequest;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Sijmen on 17-4-2017.
 */
public class NotifyRequestHandlerTest {

    private Notifier sms, fbMsg, mail;

    @Before
    public void before(){
        this.fbMsg = mock(Notifier.class);
        this.sms = mock(Notifier.class);
        this.mail = mock(Notifier.class);
    }

    @Test
    public void notifyTest() throws Exception {
        NotifyRequestHandler handler = new NotifyRequestHandler(
                new NotificationSender(sms, fbMsg, mail),
                new JsonCoder(),
                () -> new NotifyRequest(new String[]{"abc"})
        );
        handler.notify("{\n" +
                "  \"apikey\": \"abc\",\n" +
                "  \"recipient\": {\n" +
                "    \"uuid\": \"66de7d60-22ef-11e7-93ae-92361f002671\",\n" +
                "    \"name\": \"Sijmen\",\n" +
                "    \"email\": \"sijmenhuizenga@gmail.com\",\n" +
                "    \"phonenumber\": \"+31653279894\"\n" +
                "  },\n" +
                "  \"settings\": {\n" +
                "    \"receiveEmails\": true,\n" +
                "    \"receiveSms\": true,\n" +
                "    \"receiveFbMessge\": true\n" +
                "  },\n" +
                "  \"message\": \"Hello Microservices!\"\n" +
                "}");
    }
}