package it.sijmen.movienotifier.notifier.notifiers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import it.sijmen.movienotifier.coder.Coder;
import it.sijmen.movienotifier.coder.JsonCoder;
import it.sijmen.movienotifier.notifier.notifiers.FBMessengerNotifier;
import it.sijmen.movienotifier.notifier.notifiers.MailNotifier;
import it.sijmen.movienotifier.notifier.notifiers.Notifier;
import it.sijmen.movienotifier.notifier.notifiers.SMSNotifier;

import javax.inject.Named;

/**
 * Created by Sijmen on 15-4-2017.
 */
public class NotificationModule extends AbstractModule {

    private static final String
            NOTIFIER_FB_MESSENGER_TOKEN = "NOTIFIER_FB_MESSENGER_TOKEN",
            NOTIFIER_MAILGUN_SECRET = "NOTIFIER_MAILGUN_SECRET",
            NOTIFIER_MAILGUN_DOMAIN = "NOTIFIER_MAILGUN_DOMAIN",
            NOTIFIER_MAILGUN_FROMNAME = "NOTIFIER_MAILGUN_FROMNAME",
            NOTIFIER_MAILGUN_FROMEMAIL = "NOTIFIER_MAILGUN_FROMEMAIL",
            NOTIFIER_AWSSNS_ACCESSKEY = "NOTIFIER_AWSSNS_ACCESSKEY",
            NOTIFIER_AWSSNS_SECRETKEY = "NOTIFIER_AWSSNS_SECRETKEY";

    public static final String[] REQUIRED_ENV = new String[] {
            NOTIFIER_FB_MESSENGER_TOKEN,
            NOTIFIER_MAILGUN_SECRET,
            NOTIFIER_MAILGUN_DOMAIN,
            NOTIFIER_MAILGUN_FROMNAME,
            NOTIFIER_MAILGUN_FROMEMAIL,
            NOTIFIER_AWSSNS_ACCESSKEY,
            NOTIFIER_AWSSNS_SECRETKEY
    };

    @Override
    protected void configure() {
        bind(Coder.class).to(JsonCoder.class);
        bind(Notifier.class).annotatedWith(Notifier.SMS.class).to(SMSNotifier.class);
        bind(Notifier.class).annotatedWith(Notifier.MAIL.class).to(MailNotifier.class);
        bind(Notifier.class).annotatedWith(Notifier.FBMSG.class).to(FBMessengerNotifier.class);
    }

    @Provides
    @Named("fb-messenger-token")
    public String getFbMessengerApiToken(){
        return System.getenv(NOTIFIER_FB_MESSENGER_TOKEN);
    }

    @Provides
    @Named("mailgun-secret")
    public String getMailgunSecrent(){
        return System.getenv(NOTIFIER_MAILGUN_SECRET);
    }

    @Provides
    @Named("mailgun-domain")
    public String getMailgunDomain(){
        return System.getenv(NOTIFIER_MAILGUN_DOMAIN);
    }

    @Provides
    @Named("mailgun-from-name")
    public String getMailgunFromName(){
        return System.getenv(NOTIFIER_MAILGUN_FROMNAME);
    }

    @Provides
    @Named("mailgun-from-mail")
    public String getMailgunFromEmail(){
        return System.getenv(NOTIFIER_MAILGUN_FROMEMAIL);
    }

    @Provides
    @Named("aws-sns-accesskey")
    public String getAwsSnsAccesskey(){
        return System.getenv(NOTIFIER_AWSSNS_ACCESSKEY);
    }

    @Provides
    @Named("aws-sns-secretkey")
    public String getAwsSnsSecretkey(){
        return System.getenv(NOTIFIER_AWSSNS_SECRETKEY);
    }
}
