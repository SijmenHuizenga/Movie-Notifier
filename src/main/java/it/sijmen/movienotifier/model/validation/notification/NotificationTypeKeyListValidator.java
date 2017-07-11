package it.sijmen.movienotifier.model.validation.notification;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotificationTypeKeyListValidator implements ConstraintValidator<ValidNotification, List<String>> {

    @Override
    public void initialize(ValidNotification constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
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
