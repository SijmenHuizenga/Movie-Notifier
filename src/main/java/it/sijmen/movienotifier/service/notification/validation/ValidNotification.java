package it.sijmen.movienotifier.service.notification.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {
        NotificationTypeKeyArrayValidator.class,
        NotificationTypeKeyListValidator.class,
        NotificationTypeKeyValidator.class
})
@Documented
public @interface ValidNotification {
    String message() default "Notification type not valid";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
