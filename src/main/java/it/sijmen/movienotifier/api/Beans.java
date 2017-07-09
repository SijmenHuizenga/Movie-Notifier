package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.controllers.AuthenticationController;
import it.sijmen.movienotifier.controllers.UserController;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Beans {

    @Bean
    @Autowired
    public UserController userController(UserRepository repository, AuthenticationController controller){
        return new UserController(repository, controller);
    }

    @Bean
    public AuthenticationController authenticationController(){
        return new AuthenticationController();
    }

}
