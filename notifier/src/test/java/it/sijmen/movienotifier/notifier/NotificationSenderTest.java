package it.sijmen.movienotifier.notifier;

import it.sijmen.movienotifier.data.Recipient;
import it.sijmen.movienotifier.data.RecipientSettings;
import it.sijmen.movienotifier.notifier.notifiers.Notifier;

import org.junit.Before;
import org.junit.Test;
import spark.HaltException;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Sijmen on 16-4-2017.
 */
public class NotificationSenderTest {

    private Notifier sms, fbMsg, mail;

    private Recipient recipient;

    private NotificationSender sender;

    @Before
    public void before(){
        this.fbMsg = mock(Notifier.class);
        this.sms = mock(Notifier.class);
        this.mail = mock(Notifier.class);
        this.recipient = new Recipient(
                UUID.fromString("ccf9d058-2296-11e7-93ae-92361f002671"),
                "tester", "test@example.com", "+31000000000");
        this.sender = new NotificationSender(sms, fbMsg, mail);
    }

    @Test
    public void sendAll_AllSuccess() throws Exception {
        sender.sendNotifaction(recipient,
                new RecipientSettings(true, true, true),
                "hi");
        verify(sms).notify(recipient, "hi");
        verify(fbMsg).notify(recipient, "hi");
        verify(mail).notify(recipient, "hi");
    }

    @Test
    public void sendNone() throws Exception {
        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                new RecipientSettings(false, false, false),
                "hi"))
            .isInstanceOf(HaltException.class)
            .hasFieldOrPropertyWithValue("statusCode", 400);

        verify(sms, never()).notify(any(), any());
        verify(fbMsg, never()).notify(any(), any());
        verify(mail, never()).notify(any(), any());
    }

    @Test
    public void sendNotifactionTestSendOnlySMS() throws Exception {
        sender.sendNotifaction(recipient,
                new RecipientSettings(false, true, false),
                "hi");

        verify(sms).notify(recipient, "hi");
        verify(fbMsg, never()).notify(any(), any());
        verify(mail, never()).notify(any(), any());
    }

    @Test
    public void sendOnlyFB() throws Exception {
        sender.sendNotifaction(recipient,
                new RecipientSettings(false, false, true),
                "hi");

        verify(fbMsg).notify(recipient, "hi");
        verify(sms, never()).notify(any(), any());
        verify(mail, never()).notify(any(), any());
    }

    @Test
    public void sendOnlyMail() throws Exception {
        sender.sendNotifaction(recipient,
                new RecipientSettings(true, false, false),
                "hi");

        verify(mail).notify(recipient, "hi");
        verify(sms, never()).notify(any(), any());
        verify(fbMsg, never()).notify(any(), any());
    }

    @Test
    public void sendAll_SingleError() throws Exception {
        doThrow(new IOException("Failed to send sms")).when(sms).notify(recipient, "hi");

        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        new RecipientSettings(true, true, true),
                        "hi"))
                .isInstanceOf(HaltException.class)
                .hasFieldOrPropertyWithValue("statusCode", 202);
    }

    @Test
    public void sendAll_AllError() throws Exception {
        doThrow(new IOException("Failed to send sms")).when(sms).notify(recipient, "hi");
        doThrow(new IOException("Failed to send fbmsg")).when(fbMsg).notify(recipient, "hi");
        doThrow(new IOException("Failed to send mail")).when(mail).notify(recipient, "hi");

        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        new RecipientSettings(true, true, true),
                        "hi"))
                .isInstanceOf(HaltException.class)
                .hasFieldOrPropertyWithValue("statusCode", 501);
    }

    @Test
    public void sendOne_OneError() throws Exception {
        doThrow(new IOException("Failed to send mail")).when(mail).notify(recipient, "hi");

        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        new RecipientSettings(true, false, false),
                        "hi"))
                .isInstanceOf(HaltException.class)
                .hasFieldOrPropertyWithValue("statusCode", 501);
    }

    @Test
    public void nullRecipient() throws Exception {
        assertThatThrownBy(() ->
                sender.sendNotifaction(null,
                        new RecipientSettings(true, false, false),
                        "hi"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nullSettings() throws Exception {
        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        null,
                        "hi"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nullMessage() throws Exception {
        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        new RecipientSettings(true, false, false),
                        null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void emptyMessage() throws Exception {
        assertThatThrownBy(() ->
                sender.sendNotifaction(recipient,
                        new RecipientSettings(true, false, false),
                        ""))
                .isInstanceOf(IllegalArgumentException.class);
    }

}