package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.model.requests.LoginDetails;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    @Inject
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User login(
            @RequestBody(required = true) LoginDetails loginDetails) {

        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null)
            throw new UnauthorizedException();

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword()))
            throw new UnauthorizedException();
        return user;
    }

}
