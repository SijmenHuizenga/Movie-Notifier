package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.NotificationTestdata;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.service.NotificationService;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotificationTestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTestController.class);

  private final NotificationService notificationService;
  private final UserRepository userRepository;
  private final ApiKeyHelper apiKeyHelper;

  @Autowired
  public NotificationTestController(
      NotificationService notificationService,
      UserRepository userRepository,
      ApiKeyHelper apiKeyHelper) {
    this.notificationService = notificationService;
    this.userRepository = userRepository;
    this.apiKeyHelper = apiKeyHelper;
  }

  @PostMapping("/notification-test")
  @ResponseStatus(value = HttpStatus.OK)
  public void sendTestNotification(
      @RequestHeader Map<String, String> requestHeaders,
      @RequestBody NotificationTestdata testfields)
      throws IOException {
    User user = userRepository.findFirstByApikey(apiKeyHelper.getApiKey(requestHeaders));
    if (user == null) {
      throw new UnauthorizedException();
    }

    testfields.validate();

    LOGGER.info("User {} requested test-notification", user.getName());
    notificationService.sendUpdate(
        user,
        "TEST: Watcher "
            + testfields.getWatcherName()
            + " has "
            + testfields.getMatchCount()
            + " matches.",
        testfields.getBody(),
        testfields.getWatcherId(),
        testfields.getWatcherName(),
        testfields.getMatchCount(),
        testfields.getMovieid());
  }
}
