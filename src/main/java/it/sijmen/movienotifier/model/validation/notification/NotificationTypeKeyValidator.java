package it.sijmen.movienotifier.model.validation.notification;

import it.sijmen.movienotifier.service.notification.NotificationConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotificationTypeKeyValidator implements ConstraintValidator<ValidNotification, String> {

    static boolean isValidKey(@NotNull String key){
        for(String valid : NotificationConfiguration.allNotificationTypes())
            if(valid.equals(key))
                return true;
        return false;
    }

    @Override
    public void initialize(ValidNotification constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || isValidKey(value);
    }
}
