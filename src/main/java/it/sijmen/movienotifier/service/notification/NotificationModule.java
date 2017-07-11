package it.sijmen.movienotifier.service.notification;

import com.google.common.collect.Lists;
import it.sijmen.movienotifier.model.Notifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NotificationModule {

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

    @Bean(name = "fb-messenger-token")
    public String getFbMessengerApiToken(){
        return System.getenv().get(NOTIFIER_FB_MESSENGER_TOKEN);
    }

    @Bean(name = "mailgun-secret")
    public String getMailgunSecrent(){
        return System.getenv().get(NOTIFIER_MAILGUN_SECRET);
    }

    @Bean(name = "mailgun-domain")
    public String getMailgunDomain(){
        return System.getenv().get(NOTIFIER_MAILGUN_DOMAIN);
    }

    @Bean(name = "mailgun-from-name")
    public String getMailgunFromName(){
        return System.getenv().get(NOTIFIER_MAILGUN_FROMNAME);
    }

    @Bean(name = "mailgun-from-mail")
    public String getMailgunFromEmail(){
        return System.getenv().get(NOTIFIER_MAILGUN_FROMEMAIL);
    }

    @Bean(name = "aws-sns-accesskey")
    public String getAwsSnsAccesskey(){
        return System.getenv().get(NOTIFIER_AWSSNS_ACCESSKEY);
    }

    @Bean(name = "aws-sns-secretkey")
    public String getAwsSnsSecretkey(){
        return System.getenv().get(NOTIFIER_AWSSNS_SECRETKEY);
    }

    @Bean(name = "default-notifications")
    public List<String> defaultNotificationTypes(){
        return Lists.newArrayList(
            FBMessengerNotifier.ID
        );
    }

    public static List<String> allNotificationTypes(){
        return Lists.newArrayList(
            FBMessengerNotifier.ID,
            SMSNotifier.ID,
            MailNotifier.ID
        );
    }

    @Bean
    public List<Notifier> allNotifiers(
            FBMessengerNotifier fbMessengerNotifier,
            SMSNotifier smsNotifier,
            MailNotifier mailNotifier){
        return Lists.newArrayList(
                fbMessengerNotifier,
                smsNotifier,
                mailNotifier
        );
    }

}
