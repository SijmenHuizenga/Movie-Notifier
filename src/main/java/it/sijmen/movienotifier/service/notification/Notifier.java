package it.sijmen.movienotifier.service.notification;

import io.swagger.model.NotificationType;
import it.sijmen.movienotifier.model.User;

import java.io.IOException;

public abstract class Notifier {

    public abstract void notify(User recipient, String message) throws IOException;

    public abstract String getId();
    public abstract String getName();
    public abstract String getDescription();

    public NotificationType toSwagger(){
        return new NotificationType()
                .description(getDescription())
                .key(getId())
                .name(getName());
    }

}
