package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.service.notification.Notifier;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class NotificationTypeService {

    private final List<Notifier> allNotifiers;

    @Inject
    public NotificationTypeService(List<Notifier> allNotifiers) {
        this.allNotifiers = allNotifiers;
    }

    public List<Notifier> getAllNotifiers(){
        return allNotifiers;
    }

    public Notifier getNotifier(@NotNull String notifierKey){
        return allNotifiers.stream().filter(
                n -> n.getId().equals(notifierKey)
        ).findFirst().orElseThrow(
                () -> new BadRequestException("Notificationtype with given key not found.")
        );
    }

}
