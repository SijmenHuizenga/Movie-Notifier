package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import it.sijmen.movienotifier.util.ModelUpdater;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserRepository userRepository;
  private final ApiKeyHelper apiKeyHelper;
  private final ModelUpdater modelUpdater;
  private final WatcherRepository watcherRepository;

  @Autowired
  public UserController(
      UserRepository userRepository,
      ApiKeyHelper apiKeyHelper,
      WatcherRepository watcherRepository,
      ModelUpdater modelUpdater) {
    this.userRepository = userRepository;
    this.apiKeyHelper = apiKeyHelper;
    this.watcherRepository = watcherRepository;
    this.modelUpdater = modelUpdater;
  }

  @PutMapping("/user")
  public HttpEntity<User> putUser(@RequestBody User newUser) {
    newUser.setApikey(apiKeyHelper.randomAPIKey());
    newUser.setCreated(new Date());

    newUser.validate();

    newUser.validateUniqueness(userRepository);
    newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
    newUser.setId(UUID.randomUUID().toString());
    userRepository.save(newUser);

    LOGGER.trace("User stored: {}", newUser);

    return ResponseEntity.ok(newUser);
  }

  @GetMapping("/user/{userid:.*}")
  public HttpEntity<User> getUser(
      @PathVariable String userid, @RequestHeader Map<String, String> requestHeaders) {
    User user = getUserObject(userid, requestHeaders);

    LOGGER.trace("read user {}", user.getName());
    user.setPassword("xxxx");

    return ResponseEntity.ok(user);
  }

  @PostMapping("/user/{userid:.*}")
  public HttpEntity<User> updateUser(
      @PathVariable(required = false) String userid,
      @RequestHeader(required = false) Map<String, String> requestHeaders,
      @RequestBody User newUserData) {
    User updatingUser = getUserObject(userid, requestHeaders);

    modelUpdater.applyUpdates(updatingUser, newUserData);

    if (!PasswordAuthentication.isHashed(updatingUser.getPassword()))
      updatingUser.setPassword(PasswordAuthentication.hash(updatingUser.getPassword()));

    updatingUser.validate();

    updatingUser.validateUniqueness(userRepository);
    userRepository.save(updatingUser);

    LOGGER.trace("Updated user {}", updatingUser.getName());

    return ResponseEntity.ok(updatingUser);
  }

  @DeleteMapping("/user/{userid:.*}")
  public HttpEntity<User> deleteUser(
      @PathVariable String userid, @RequestHeader Map<String, String> requestHeaders) {
    User user = getUserObject(userid, requestHeaders);

    userRepository.delete(user);
    watcherRepository.deleteWatchersByUserid(user.getId());
    LOGGER.trace("deleted user {} and all it's watchers.", user.getName());

    return ResponseEntity.ok(user);
  }

  private User getUserObject(String userid, Map<String, String> requestHeaders) {
    apiKeyHelper.checkApiKeyExistence(requestHeaders);
    User user = userRepository.getFirstByUuid(userid);
    String apikey = apiKeyHelper.getApiKey(requestHeaders);

    User executingUser = userRepository.findFirstByApikey(apikey);
    if (executingUser == null) {
      LOGGER.trace("Could not find user with apikey {}", apikey);
      throw new UnauthorizedException();
    }

    if (user == null || !executingUser.getId().equals(user.getId())) {
      LOGGER.warn("Not authorized to act on this user.");
      throw new UnauthorizedException();
    }
    return user;
  }
}
