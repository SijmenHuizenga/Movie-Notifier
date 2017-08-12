package it.sijmen.movienotifier.service.notification;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.service.notification.notifiers.FBMessengerNotifier;
import it.sijmen.movienotifier.service.notification.notifiers.MailNotifier;
import it.sijmen.movienotifier.service.notification.notifiers.SMSNotifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "notification")
public class NotificationConfiguration {

    @NestedConfigurationProperty
    private Facebook facebook;

    @NestedConfigurationProperty
    private Mailgun mailgun;

    @NestedConfigurationProperty
    private AWSSNS awssns;

    public static class Facebook {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public static class Mailgun {

        private String apikey, domnain;
        @NestedConfigurationProperty
        private From from;

        public String getDomnain() {
            return domnain;
        }

        public void setDomnain(String domnain) {
            this.domnain = domnain;
        }

        public From getFrom() {
            return from;
        }

        public void setFrom(From from) {
            this.from = from;
        }

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public static class From {
            private String name, mail;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }
        }
    }

    public static class AWSSNS {
        String key;
        String secret;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    @Bean(name = "default-notifications")
    public List<String> defaultNotificationTypes(){
        return Collections.singletonList(FBMessengerNotifier.ID);
    }

    public static List<String> allNotificationTypes(){
        return Arrays.asList(
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
        return Arrays.asList(
                fbMessengerNotifier,
                smsNotifier,
                mailNotifier
        );
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public Mailgun getMailgun() {
        return mailgun;
    }

    public void setMailgun(Mailgun mailgun) {
        this.mailgun = mailgun;
    }

    public AWSSNS getAwssns() {
        return awssns;
    }

    public void setAwssns(AWSSNS awssns) {
        this.awssns = awssns;
    }

}
