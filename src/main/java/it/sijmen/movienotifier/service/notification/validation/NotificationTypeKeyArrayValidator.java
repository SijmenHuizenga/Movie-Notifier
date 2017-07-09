package it.sijmen.movienotifier.service.notification.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotificationTypeKeyArrayValidator implements ConstraintValidator<ValidNotification, String[]> {

    @Override
    public void initialize(ValidNotification constraintAnnotation) {

    }

    @Override
    public boolean isValid(String[] values, ConstraintValidatorContext context) {
        if(values == null)
            return true;
        for(String value : values){
            if(value == null)
                continue;
            if(!NotificationTypeKeyValidator.isValidKey(value))
                return false;
        }
        return true;
    }
}
