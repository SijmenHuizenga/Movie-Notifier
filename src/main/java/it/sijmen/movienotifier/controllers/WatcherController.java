package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import it.sijmen.movienotifier.util.ModelUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class WatcherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherController.class);

    private final ModelUpdater modelUpdater;
    private final ApiKeyHelper apiKeyHelper;
    private final UserRepository userRepository;
    private final WatcherRepository watcherRepo;

    @Autowired
    public WatcherController(ModelUpdater modelUpdater,
                             ApiKeyHelper apiKeyHelper,
                             UserRepository userRepository,
                             WatcherRepository watcherRepo) {
        this.modelUpdater = modelUpdater;
        this.apiKeyHelper = apiKeyHelper;
        this.userRepository = userRepository;
        this.watcherRepo = watcherRepo;
    }

    @PutMapping("/watchers")
    public HttpEntity newWatcher(@RequestBody Watcher newWatcher,
                                 @RequestHeader Map<String, String> requestHeaders) {
        User user = ensureLoggedIn(requestHeaders);
        ensureUserOwnsWatcher(newWatcher, user);

        newWatcher.setId(UUID.randomUUID().toString());
        newWatcher.validate();

        watcherRepo.save(newWatcher);

        LOGGER.trace("Watcher stored: {}", newWatcher);

        return ResponseEntity.ok(newWatcher);
    }

    @GetMapping("/watchers/{watcherid}")
    public HttpEntity getWatcher(@PathVariable(required = false) String watcherid,
                                 @RequestHeader Map<String, String> requestHeaders) {
        User user = ensureLoggedIn(requestHeaders);

        Watcher watcher = watcherRepo.getFirstByUuid(watcherid);
        if(watcher == null) {
            throw new BadRequestException("Watcher does not exist");
        }

        if(!user.getId().equals(watcher.getUserid()))
            watcher.setId(null);

        return ResponseEntity.ok(watcher);
    }

    @GetMapping("/watchers")
    public HttpEntity getWatcher(@RequestHeader Map<String, String> requestHeaders) {
        User user = ensureLoggedIn(requestHeaders);
        List<Watcher> watchers = watcherRepo.getAllByUserid(user.getId());
        return ResponseEntity.ok(watchers);
    }

    @PostMapping("/watchers/{watcherid}")
    public HttpEntity updateWatcher(
            @PathVariable(required = false) String watcherid,
            @RequestBody Watcher newWatcherData,
            @RequestHeader Map<String, String> requestHeaders) {
        User user = ensureLoggedIn(requestHeaders);

        Watcher watcher = watcherRepo.getFirstByUuid(watcherid);
        if(watcher == null) {
            throw new BadRequestException("The watcher you are trying to update does not exist");
        }

        ensureUserOwnsWatcher(watcher, user);

        if(newWatcherData.getUserid() != null && !newWatcherData.getUserid().equals(watcher.getUserid())) {
            throw new BadRequestException("Not allowed to changed the userid of a watcher");
        }

        modelUpdater.applyUpdates(watcher, newWatcherData);
        watcher.validate();
        watcherRepo.save(watcher);
        LOGGER.trace("Updated watcher {}: {}", watcher.getId(), watcher.getName());

        return ResponseEntity.ok(watcher);
    }

    @DeleteMapping("/watchers/{watcherid}")
    public HttpEntity deleteWatcher(@PathVariable(required = false) String watcherid,
                                    @RequestHeader Map<String, String> requestHeaders) {
        User user = ensureLoggedIn(requestHeaders);
        Watcher watcher = watcherRepo.getFirstByUuid(watcherid);
        if(watcher == null) {
            throw new BadRequestException("Watcher does not exist");
        }

        ensureUserOwnsWatcher(watcher, user);
        watcherRepo.delete(watcher);
        return ResponseEntity.ok().build();
    }

    private void ensureUserOwnsWatcher(Watcher watcher, User user) {
        if(user == null || !user.getId().equals(watcher.getUserid())) {
            throw new UnauthorizedException();
        }
    }

    private User ensureLoggedIn(Map<String, String> requestHeaders) {
        User executingUser = userRepository.findFirstByApikey(apiKeyHelper.getApiKey(requestHeaders));
        if(executingUser == null)
            throw new UnauthorizedException();
        return executingUser;
    }
}
