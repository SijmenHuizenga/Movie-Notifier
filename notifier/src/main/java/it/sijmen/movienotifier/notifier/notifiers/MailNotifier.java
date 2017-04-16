package it.sijmen.movienotifier.notifier.notifiers;

import it.sijmen.movienotifier.data.Recipient;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by Sijmen on 7-4-2017.
 */
class MailNotifier extends Notifier {

    private Configuration mailgunConfig;

    @Inject
    public MailNotifier(
            @Named("mailgun-secret") String apikey,
            @Named("mailgun-domain") String domain,
            @Named("mailgun-from-name") String fromname,
            @Named("mailgun-from-mail") String fromemail
    ) {
        mailgunConfig = new Configuration()
                .domain(domain)
                .apiKey(apikey)
                .from(fromname, fromemail);
    }

    @Override
    public void notify(Recipient recipient, String message) throws IOException {
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
}
