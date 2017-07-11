package it.sijmen.movienotifier.model.validation.date;

import it.sijmen.movienotifier.model.validation.notification.NotificationTypeKeyArrayValidator;
import it.sijmen.movienotifier.model.validation.notification.NotificationTypeKeyListValidator;
import it.sijmen.movienotifier.model.validation.notification.NotificationTypeKeyValidator;

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
        PresentOrFutureValidator.class
})
@Documented
public @interface DateFuture {
    String message() default "Date must be in the present or future";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
