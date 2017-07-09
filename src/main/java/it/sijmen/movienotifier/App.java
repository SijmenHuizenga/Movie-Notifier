package it.sijmen.movienotifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"it.sijmen.movienotifier"})
public class App implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {}

    public static void main(String[] args) throws Exception {
        new SpringApplication(App.class).run(args);
    }

}
