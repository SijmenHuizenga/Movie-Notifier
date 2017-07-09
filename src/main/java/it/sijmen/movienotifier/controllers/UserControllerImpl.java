package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerImpl {

    private final UserRepository userRepository;

    @Autowired
    public UserControllerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        newUser.validate();
        newUser.validateUniqueness(userRepository);

        return userRepository.save(newUser);
    }
}
