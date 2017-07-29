package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class WatcherService {

    private final AuthenticationService authService;
    private final WatcherRepository watcherRepo;
    private final UserRepository userRepo;

    public WatcherService(AuthenticationService authService, WatcherRepository watcherRepo, UserRepository userRepo) {
        this.authService = authService;
        this.watcherRepo = watcherRepo;
        this.userRepo = userRepo;
    }

    public Watcher createWatcher(@NotNull String apikey, @NotNull Watcher watcher) {
        User executingUser = userRepo.findFirstByApikey(apikey);
        if(executingUser == null || !authService.canCreate(executingUser, watcher))
            throw new UnauthorizedException();

        watcher.validate();
        watcher.validateUniqueness(watcherRepo);
        return watcherRepo.save(watcher);
    }
}
