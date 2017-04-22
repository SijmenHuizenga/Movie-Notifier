package it.sijmen.movienotifier.notifier;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import it.sijmen.movienotifier.coder.Coder;
import it.sijmen.movienotifier.notifier.notifiers.*;
import it.sijmen.movienotifier.service.AbstractService;

import static spark.Spark.*;

/**
 * Created by Sijmen on 13-4-2017.
 */
public class NotifierService extends AbstractService {

    @Inject
    private NotifyRequestHandler notifyRequestHandler;

    @Inject
    public NotifierService(Coder coder) {
        super(coder, NotificationModule.REQUIRED_ENV);
    }

    private void start(){
        post("/notify", (req, resp) -> {
            notifyRequestHandler.notify(req.body());
            return "";
        });
        get("/ping", (req, resp) -> "The service is ok!");
    }

    public static void main(String[] args) {
        Injector injector;
        try{
            injector = Guice.createInjector(new NotificationModule());
        }catch (Exception e){
            System.err.println("Initialisation failed: " + e.getMessage());
            return;
        }
        NotifierService instance = injector.getInstance(NotifierService.class);
        instance.start();
    }

}
