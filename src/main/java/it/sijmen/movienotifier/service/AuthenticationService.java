package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean canCreate(@NotNull User executingUser, @NotNull Watcher watcher) {
        return executingUser.getId().equals(watcher.getUser());
    }
}
