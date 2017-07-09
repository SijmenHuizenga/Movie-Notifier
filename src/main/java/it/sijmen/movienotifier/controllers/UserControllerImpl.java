package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.LoginDetails;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerImpl{

    private final UserRepository userRepository;

    @Autowired
    public UserControllerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        newUser.validate();
        newUser.validateUniqueness(userRepository);

        newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User login(LoginDetails loginDetails) {
        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null)
            throw new UnauthorizedException();

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword()))
            throw new UnauthorizedException();

        return user;
    }
}
