package it.sijmen.movienotifier.service.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
    private UserRepository userRepository;

    @Autowired
    public NotificationService(
            UserRepository userRepository,
            @Value("${gcm.serviceaccountkeyfile}") String serviceAccountKeyFile) throws IOException {
        this.userRepository = userRepository;

        if(!"disabled".equals(serviceAccountKeyFile))
        FirebaseApp.initializeApp(
                new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(serviceAccountKeyFile)))
                        .build()
        );
    }

    public void notify(String userid, String messageTitle, String messageBody) {
        User user = userRepository.getFirstByUuid(userid);
        if(user == null) {
            LOGGER.error("Cannot notify user {} because it does not exist.", userid);
            return;
        }

        for (String token : user.getRegistrationTokens()) {
            try {
                FirebaseMessaging.getInstance().send(
                        Message.builder()
                                .setNotification(new Notification(messageTitle, messageBody))
                                .setToken(token)
                                .build()
                );
                LOGGER.info("Sent message to {} token {} title: {}", user.getName(), token, messageTitle);
            } catch (FirebaseMessagingException e) {
                LOGGER.error("Could not send message to {} token: {} title: {}", user.getName(), token, messageTitle, e);
            }
        }
    }
}
