package it.sijmen.movienotifier.notifier.requests;

import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Sijmen on 16-4-2017.
 */
public class NotifyRequestTest {

    private NotifyRequest req;
    private Recipient recipient;
    private RecipientSettings settings;

    @Before
    public void setUp() throws Exception {
        this.req = spy(new NotifyRequest());
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
        req.setApikey("F5735948F1A6C8A4F79AD6B80D67FED6BD8F6AED974CC8BF6D5EB7CD291406A1");
        assertTrue(req.authorized());
    }

    @Test
    public void testApiKey_ok2(){
        req.setApikey("75B3A3BD95EF49CBE128FD57F991559DD57AD5F4CAAC762645F5A7710889CD52");
        assertTrue(req.authorized());
    }

    @Test
    public void testApiKey_ok3(){
        req.setApikey("4F3202D18C43CABD5F512087C1A8AFF97FD9100FFD6401E91AB0CD43A58D39B6");
        assertTrue(req.authorized());
    }

    @Test
    public void testApiKey_ok4(){
        req.setApikey("CD0CB3D0AD361ACAC6D23AD03E652321B0EB875762CD7F5E1A7CFE9D4824E13D");
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