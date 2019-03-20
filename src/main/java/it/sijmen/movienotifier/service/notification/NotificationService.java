package it.sijmen.movienotifier.service.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.service.pathe.api.PatheShowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

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

    public void sendUpdates(Watcher watcher, List<PatheShowing> matches) {
        if(matches.size() == 0) {
            return;
        }

        User user = userRepository.getFirstByUuid(watcher.getId());
        if(user == null) {
            LOGGER.error("Could not send notification to user {} because it does not exit.");
            return;
        }

        LOGGER.trace("Notifying user about {} matches for watcher {}", matches.size(), watcher.getId());

        Collections.sort(matches);

        String body = matches.stream()
                .sorted()
                .map(PatheShowing::toMessageString)
                .collect(Collectors.joining(lineSeparator()));

        String header = makeMessageHeader(watcher, matches.size());

        LOGGER.info("Sent notifications to {} with header {}", user.getName(), header);

        if(user.getEmail() != null) {
            //todo
        }

        AndroidConfig config = AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build();

        List<Message> messages = user.getRegistrationTokens().stream().map(token ->
                Message.builder()
                        .setToken(token)
                        .putData("user.id", user.getId())
                        .putData("watcher", watcher.getId())
                        .putData("watcher.name", watcher.getName())
                        .putData("matches.count", Integer.toString(matches.size()))
                        .putData("body", body)
                        .setAndroidConfig(config)
                        .build()
        ).collect(Collectors.toList());

        try {
            FirebaseMessaging.getInstance().sendAll(messages);
        } catch (FirebaseMessagingException e) {
            LOGGER.error("Could not send fb messages to {}. title: {}", user.getName(), header, e);
        }
    }

    private String makeMessageHeader(Watcher watcher, int matches) {
        return watcher.getName() + lineSeparator() + "+" + matches + " matches";
    }

}
