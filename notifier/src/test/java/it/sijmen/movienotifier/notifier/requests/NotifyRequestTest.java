package it.sijmen.movienotifier.notifier.requests;

import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Sijmen on 16-4-2017.
 */
public class NotifyRequestTest {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    private NotifyRequest req;
    private Recipient recipient;
    private RecipientSettings settings;

    @Before
    public void setUp() throws Exception {
        this.req = spy(new NotifyRequest(new String[]{"abc", "def"}));
        this.recipient = new Recipient(
                UUID.fromString("ccf9d058-2296-11e7-93ae-92361f002671"),
                "tester", "test@example.com", "+31000000000");
        this.settings = new RecipientSettings(true, true, true);
    }

    @Test
    public void isValid_Valid() throws Exception {
        when(req.authorized()).thenReturn(true);
        req.setMessage("i am a message");
        req.setRecipient(recipient);
        req.setSettings(settings);
        assertTrue(req.isValid());
    }

    @Test
    public void isValid_NoMessage() throws Exception {
        when(req.authorized()).thenReturn(true);
        req.setRecipient(recipient);
        req.setSettings(settings);
        assertFalse(req.isValid());
    }

    @Test
    public void isValid_NoRecipient() throws Exception {
        when(req.authorized()).thenReturn(true);
        req.setMessage("i am a message");
        req.setSettings(settings);
        assertFalse(req.isValid());
    }

    @Test
    public void isValid_NoSettings() throws Exception {
        when(req.authorized()).thenReturn(true);
        req.setMessage("i am a message");
        req.setRecipient(recipient);
        assertFalse(req.isValid());
    }

    @Test
    public void isValid_Empty() throws Exception {
        when(req.authorized()).thenReturn(true);
        assertFalse(req.isValid());
    }

    @Test
    public void testApiKey_ok1(){
        req.setApikey("abc");
        assertTrue(req.authorized());
    }

    @Test
    public void testApiKey_ok2(){
        req.setApikey("def");
        assertTrue(req.authorized());
    }

    @Test
    public void testApiKey_Null(){
        req.setApikey(null);
        assertFalse(req.authorized());
    }
    @Test
    public void testApiKey_Wrong(){
        req.setApikey("adsfadsfasdfadsf");
        assertFalse(req.authorized());
    }

}