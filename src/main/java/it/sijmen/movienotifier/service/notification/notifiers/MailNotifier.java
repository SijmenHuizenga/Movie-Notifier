package it.sijmen.movienotifier.service.notification.notifiers;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.User;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Component
public class MailNotifier extends Notifier {

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
    public void notify(User recipient, String message) throws IOException {
        Response response = Mail.using(mailgunConfig)
                .to(recipient.getEmail())
                .subject(getTitle(message))
                .text(message)
                .build()
                .send();
        if(!response.isOk())
            throw new IOException("Mailgun returned not ok. Code: " + response.responseCode()
                    + ". Message: " + response.responseMessage());
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
