package it.sijmen.movienotifier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"it.sijmen.movienotifier"})
@EnableScheduling
public class App implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {}

    public static void main(String[] args) throws Exception {
        new SpringApplication(App.class).run(args);
    }

}
