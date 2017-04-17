package it.sijmen.movienotifier.configurator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import it.sijmen.movienotifier.coder.Coder;
import it.sijmen.movienotifier.service.AbstractService;

import static spark.Spark.get;

/**
 * Created by Sijmen on 17-4-2017.
 */
public class ConfiguratorService extends AbstractService {

    @Inject
    public ConfiguratorService(Coder coder) {
        super(coder);
    }

    void start(){
        get("/ping", (req, resp) -> "The service is ok.");
    }

    public static void main(String[] args) {
        Injector injector;
        try{
            injector = Guice.createInjector(new ConfiguratorModule());
        }catch (Exception e){
            System.err.println("Initialisation failed: " + e.getMessage());
            return;
        }
        ConfiguratorService instance = injector.getInstance(ConfiguratorService.class);
        instance.start();
    }
}
