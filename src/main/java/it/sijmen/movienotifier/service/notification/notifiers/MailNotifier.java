package it.sijmen.movienotifier.service.notification.notifiers;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.User;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Component
public class MailNotifier implements Notifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailNotifier.class);

    public static final String ID = "MIL";

    private Configuration mailgunConfig;

    @Inject
    public MailNotifier(
            @Value("${notification.mailgun.domnain}") String domain,
            @Value("${notification.mailgun.apikey}") String apikey,
            @Value("${notification.mailgun.from.name}") String fromName,
            @Value("${notification.mailgun.from.mail}") String fromMail) {
        mailgunConfig = new Configuration()
                .domain(domain)
                .apiKey(apikey)
                .from(fromName, fromMail);
    }

    @Override
    public void notify(User recipient, String messageHeader, String messageBody) throws IOException {
        Response response = Mail.using(mailgunConfig)
                .to(recipient.getEmail())
                .subject(getTitle(messageHeader))
                .text(messageHeader + System.lineSeparator() + messageBody)
                .build()
                .send();
        if(!response.isOk())
            throw new IOException("Mailgun returned not ok. Code: " + response.responseCode()
                    + ". Message: " + response.responseMessage());
        LOGGER.trace("Sent mail message through mailgun to {}. Message: {} {}", recipient.getEmail(), messageHeader, messageBody);
    }

    private String getTitle(String message) {
        return message.length() > 20 ? message.substring(0, 20) + "..." : message;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "E-Mail";
    }

    @Override
    public String getDescription() {
        return "Get Mail message on your email address. This communication type can be a bit slow.";
    }
}
