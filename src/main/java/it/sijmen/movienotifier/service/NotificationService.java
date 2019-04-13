package it.sijmen.movienotifier.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.service.pathe.api.PatheShowing;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;
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
    private Configuration mailgunConfig;

    @Autowired
    public NotificationService(
            UserRepository userRepository,
            @Value("${fcm.serviceaccountkeyfile}") String serviceAccountKeyFile,
            @Value("${mailgun.domnain}") String domain,
            @Value("${mailgun.apikey}") String apikey,
            @Value("${mailgun.from.name}") String fromName,
            @Value("${mailgun.from.mail}") String fromMail) throws IOException {
        this.userRepository = userRepository;
        mailgunConfig = new Configuration()
                .domain(domain)
                .apiKey(apikey)
                .from(fromName, fromMail);

        if(!"disabled".equals(serviceAccountKeyFile))
        FirebaseApp.initializeApp(
                new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(serviceAccountKeyFile)))
                        .build()
        );
    }

    public void sendUpdates(Watcher watcher, List<PatheShowing> matches) {
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

        try {
            sendUpdate(user, header, body, watcher.getId(), watcher.getName(), matches.size(), watcher.getMovieid());
        } catch (IOException e) {
            LOGGER.error("Could not send notification to {}. title: {}. Caused By:\n", user.getName(), header, e.getMessage());
        }
    }

    public void sendUpdate(User user, String header, String body, String watcherId, String watcherName, int matchCount, int movieid) throws IOException {
        LOGGER.info("Sending notifications to {} with header {}", user.getName(), header);

        IOException mailError = null;
        IOException firebaseError = null;

        if(user.getEmail() != null && !user.getEmail().equals("")) {
            try {
                sendEmail(user.getEmail(), header, body);
            }catch (IOException e) {
                mailError = e;
            }
        }

        try {
            sendAndroidNotification(user, body, watcherId, watcherName, matchCount, movieid);
        }catch (IOException e) {
            firebaseError = e;
        }

        if(mailError != null && firebaseError != null) {
            throw new IOException("Notification to mail and firebase failed: \n" + mailError.getMessage() + "\n" + firebaseError.getMessage());
        } else if(mailError != null) {
            throw new IOException("Notification to mail failed: \n" + mailError.getMessage());
        } else if(firebaseError != null) {
            throw new IOException("Notification to firebase failed: \n" + firebaseError.getMessage());
        }

    }

    private void sendAndroidNotification(User user, String body, String watcherId, String watcherName, int matchCount, int movieid) throws IOException {
        AndroidConfig config = AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build();

        List<Message> messages = user.getRegistrationTokens().stream().map(token ->
                Message.builder()
                        .setToken(token)
                        .putData("user.id", user.getId())
                        .putData("watcher.id", watcherId)
                        .putData("watcher.name", watcherName)
                        .putData("watcher.movieid", Integer.toString(movieid))
                        .putData("matches.count", Integer.toString(matchCount))
                        .putData("body", body)
                        .setAndroidConfig(config)
                        .build()
        ).collect(Collectors.toList());

        if(messages.isEmpty())
            return;
        BatchResponse response;
        try {
            LOGGER.info("Sending notifications to {} devices.", messages.size());
            response = FirebaseMessaging.getInstance().sendAll(messages);
            if(response == null)
                throw new IOException("Firebase returned null.");
        } catch (FirebaseMessagingException e) {
            throw new IOException("Something went wront at firebase: " + e.getErrorCode(), e);
        }

        if(response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            StringBuilder failureMessage = new StringBuilder();
            for (int i = 0; i < responses.size(); i++) {
                SendResponse r = responses.get(i);
                if(r.isSuccessful())
                    continue;
                failureMessage
                        .append(user.getRegistrationTokens().get(i))
                        .append(": ")
                        .append(r.getException().getErrorCode())
                        .append("\n");
            }

            String error = String.format("%d of the %d messages failed: \n%s", response.getFailureCount(), messages.size(), failureMessage.toString());
            throw new IOException(error);
        }
    }

    private void sendEmail(String email, String messageHeader, String messageBody) throws IOException {
        Response response = Mail.using(mailgunConfig)
                .to(email)
                .subject(getTitle(messageHeader))
                .text(messageHeader + System.lineSeparator() + System.lineSeparator() + messageBody)
                .build()
                .send();
        if(!response.isOk()) {
            LOGGER.error("Mailgun returned not ok. Code: {}. Message: {}", response.responseCode(), response.responseMessage());
            throw new IOException("Mailgun returned not ok. code: " + response.responseCode() + ", Message: " + response.responseMessage());
        }

        LOGGER.trace("Sent mail message through mailgun to {}. Message: {} {}", email, messageHeader, messageBody);
    }

    private String getTitle(String message) {
        return message.length() > 20 ? message.substring(0, 20) + "..." : message;
    }

    private String makeMessageHeader(Watcher watcher, int matches) {
        return watcher.getName() + lineSeparator() + "+" + matches + " matches";
    }

}
