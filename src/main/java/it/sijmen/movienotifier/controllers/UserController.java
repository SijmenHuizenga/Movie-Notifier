package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.LoginDetails;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.jetbrains.annotations.NotNull;

public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationController authController;

    @Autowired
    public UserController(UserRepository userRepository, AuthenticationController authenticationService) {
        this.userRepository = userRepository;
        this.authController = authenticationService;
    }

    public User createUser(@NotNull User newUser) {
        newUser.validate();
        newUser.validateUniqueness(userRepository);

        newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User login(@NotNull LoginDetails loginDetails) {
        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null)
            throw new UnauthorizedException();

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword()))
            throw new UnauthorizedException();

        return user;
    }

    public void delete(@NotNull String userid, @NotNull String apikey) {
        User executingUser = userRepository.findFirstByApikey(apikey);
        User toDeleteUser = userRepository.findFirstById(userid);
        if(executingUser == null || toDeleteUser == null)
            throw new UnauthorizedException();
        if(!authController.canDelete(executingUser, toDeleteUser))
            throw new UnauthorizedException();
        userRepository.delete(toDeleteUser);
        //todo: delete from all other places?
        //todo: set inactive? Soft deletes?
    }
}
