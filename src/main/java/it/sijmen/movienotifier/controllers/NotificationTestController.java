package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.NotificationTestdata;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.service.NotificationService;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Controller
public class NotificationTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTestController.class);

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final ApiKeyHelper apiKeyHelper;

    @Autowired
    public NotificationTestController(NotificationService notificationService,
                                      UserRepository userRepository,
                                      ApiKeyHelper apiKeyHelper) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.apiKeyHelper = apiKeyHelper;
    }

    @PostMapping("/notification-test")
    public void sendTestNotification(@RequestHeader Map<String, String> requestHeaders,
                                     @RequestBody NotificationTestdata testfields) {
        User user = userRepository.findFirstByApikey(apiKeyHelper.getApiKey(requestHeaders));
        if (user == null) {
            throw new UnauthorizedException();
        }

        testfields.validate();

        LOGGER.info("User {} requested test-notification", user.getName());

        try {
            notificationService.sendUpdate(user,
                    "TEST: Watcher " + testfields.getWatcherName() + " has " + testfields.getMatchCount() + " matches.",
                    testfields.getBody(), testfields.getWatcherId(),
                    testfields.getWatcherName(), testfields.getMatchCount(), testfields.getMovieid());
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            throw new RuntimeException(e.getMessage() + "\nCaused by: \n" + sStackTrace);
        }

    }

}
