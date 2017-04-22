package it.sijmen.movienotifier.notifier.notifiers;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import it.sijmen.movienotifier.coder.Coder;
import it.sijmen.movienotifier.coder.JsonCoder;
import it.sijmen.movienotifier.env.Env;

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
            NOTIFIER_AWSSNS_SECRETKEY = "NOTIFIER_AWSSNS_SECRETKEY",
            NOTIFIER_APIKEYS = "NOTIFIER_APIKEYS";

    public static final String[] REQUIRED_ENV = new String[] {
            NOTIFIER_FB_MESSENGER_TOKEN,
            NOTIFIER_MAILGUN_SECRET,
            NOTIFIER_MAILGUN_DOMAIN,
            NOTIFIER_MAILGUN_FROMNAME,
            NOTIFIER_MAILGUN_FROMEMAIL,
            NOTIFIER_AWSSNS_ACCESSKEY,
            NOTIFIER_AWSSNS_SECRETKEY,
            NOTIFIER_APIKEYS
    };

    @Override
    protected void configure() {
        bind(Notifier.class).annotatedWith(Notifier.SMS.class).to(SMSNotifier.class);
        bind(Notifier.class).annotatedWith(Notifier.MAIL.class).to(MailNotifier.class);
        bind(Notifier.class).annotatedWith(Notifier.FBMSG.class).to(FBMessengerNotifier.class);
    }

    @Provides
    @Inject
    @Named("fb-messenger-token")
    public String getFbMessengerApiToken(Env env){
        return env.getenv(NOTIFIER_FB_MESSENGER_TOKEN);
    }

    @Provides
    @Inject
    @Named("mailgun-secret")
    public String getMailgunSecrent(Env env){
        return env.getenv(NOTIFIER_MAILGUN_SECRET);
    }

    @Provides
    @Inject
    @Named("mailgun-domain")
    public String getMailgunDomain(Env env){
        return env.getenv(NOTIFIER_MAILGUN_DOMAIN);
    }

    @Provides
    @Inject
    @Named("mailgun-from-name")
    public String getMailgunFromName(Env env){
        return env.getenv(NOTIFIER_MAILGUN_FROMNAME);
    }

    @Provides
    @Inject
    @Named("mailgun-from-mail")
    public String getMailgunFromEmail(Env env){
        return env.getenv(NOTIFIER_MAILGUN_FROMEMAIL);
    }

    @Provides
    @Inject
    @Named("aws-sns-accesskey")
    public String getAwsSnsAccesskey(Env env){
        return env.getenv(NOTIFIER_AWSSNS_ACCESSKEY);
    }

    @Provides
    @Inject
    @Named("aws-sns-secretkey")
    public String getAwsSnsSecretkey(Env env){
        return env.getenv(NOTIFIER_AWSSNS_SECRETKEY);
    }

    @Provides
    @Inject
    @Named("api-keys")
    public String[] getApiKeys(Env env){
        return env.getenv(NOTIFIER_APIKEYS).split(",");
    }
}
