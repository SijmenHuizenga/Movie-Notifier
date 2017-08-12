package it.sijmen.movienotifier.service.notification;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private UserRepository userRepository;
    private final List<Notifier> allNotifiers;

    public NotificationService(UserRepository userRepository, List<Notifier> allNotifiers) {
        this.userRepository = userRepository;
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

    public void notify(String userId, String message) {
        User user = userRepository.findOne(userId);
        if(user.getEnabledNotifications() == null || user.getEnabledNotifications().isEmpty()){
            LOGGER.error("Could not notify user because no notification types are enabled.", user, message);
            return;
        }
        user.getEnabledNotifications().forEach(
                t -> notify(user, message, t)
        );
    }

    private void notify(User user, String message, String notificationType) {
        Notifier notifier = getNotifier(notificationType);
        try {
            notifier.notify(user, message);
        } catch (IOException e) {
            LOGGER.error("Notification failed", e, user, message);
        }
    }
}
