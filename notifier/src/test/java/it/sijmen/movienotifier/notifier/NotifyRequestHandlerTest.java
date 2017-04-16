package it.sijmen.movienotifier.notifier;

import it.sijmen.movienotifier.coder.JsonCoder;
import it.sijmen.movienotifier.notifier.notifiers.Notifier;
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
                new JsonCoder()
        );
        handler.notify("{\n" +
                "  \"apikey\": \"F5735948F1A6C8A4F79AD6B80D67FED6BD8F6AED974CC8BF6D5EB7CD291406A1\",\n" +
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