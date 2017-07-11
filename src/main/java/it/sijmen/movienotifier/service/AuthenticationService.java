package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    boolean canDelete(@NotNull User executingUser, @NotNull User toDeleteUser) {
        return executingUser.getId().equals(toDeleteUser.getId());
    }

    boolean canSeeFullDetails(User executingUser, User searchUser) {
        return executingUser.getId().equals(searchUser.getId());
    }

    boolean canSeeUsername(User executingUser, User searchUser) {
        return true;
    }

    boolean canUpdate(User executingUser, User searchUser) {
        return executingUser.getId().equals(searchUser.getId());
    }

    public boolean allowNotification(User executingUser, String notificationKey){
        //todo: reference keys?
        return "FBM".equals(notificationKey) || "MIL".equals(notificationKey);
    }
}
