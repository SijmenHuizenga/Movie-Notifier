package it.sijmen.movienotifier;

import it.sijmen.movienotifier.controllers.UserControllerImpl;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"it.sijmen.movienotifier.api"})
public class App implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {}

    public static void main(String[] args) throws Exception {
        new SpringApplication(App.class).run(args);
    }

    @Bean
    public UserControllerImpl userController(UserRepository repository){
        return new UserControllerImpl(repository);
    }

}
