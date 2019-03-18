package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.model.requests.LoginDetails;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User login(@RequestBody LoginDetails loginDetails) {

        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null) {
            LOGGER.trace("User Login failed: unknown name {}", loginDetails);
            throw new UnauthorizedException();
        }

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword())) {
            LOGGER.trace("User Login failed: incorrect password {}", loginDetails);
            throw new UnauthorizedException();
        }
        LOGGER.trace("User Login successfull {}", loginDetails);
        return user;
    }

}
