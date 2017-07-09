package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import org.jetbrains.annotations.NotNull;

public class AuthenticationController {

    boolean canDelete(@NotNull User executingUser, @NotNull User toDeleteUser) {
        return executingUser.getId().equals(toDeleteUser.getId());
    }
}
