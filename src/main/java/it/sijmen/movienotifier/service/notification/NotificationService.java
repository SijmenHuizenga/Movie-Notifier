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

    public void notify(String userId, String messageHeader, String messageBody) {
        User user = userRepository.getFirstByUuid(userId);
        if(user == null){
            LOGGER.error("Could not find " + userId + " while this user has enabled watchers");
            return;
        }
        if(user.getEnabledNotifications() == null || user.getEnabledNotifications().isEmpty()){
            LOGGER.error("Could not notify user {} because no notification types are enabled. message: {} {}",user.getId(), messageHeader, messageBody);
            return;
        }
        user.getEnabledNotifications().forEach(
                t -> notify(user, messageHeader, messageBody, t)
        );
    }

    private void notify(User user, String messageHeader, String messageBody, String notificationType) {
        Notifier notifier = getNotifier(notificationType);
        try {
            notifier.notify(user, messageHeader, messageBody);
        } catch (Exception e) {
            LOGGER.error("Notification failed to user {} with message {} {}", user.getId(), messageHeader, messageBody, e);
        }
    }
}
