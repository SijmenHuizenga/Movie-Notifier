package it.sijmen.movienotifier;

import com.google.inject.Guice;
import com.google.inject.Injector;
import it.sijmen.movienotifier.inj.ConfigurationModule;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class Main {

    public static void main(String[] args) {
        Injector injector;
        try{
            injector = Guice.createInjector(
                    new ConfigurationModule()
                    //todo: add more injectors!
            );
        }catch (Exception e){
            System.err.println("Initialisation failed: " + e.getMessage());
            return;
        }

        MovieNotifier app = injector.getInstance(MovieNotifier.class);
        app.run();
    }

}
