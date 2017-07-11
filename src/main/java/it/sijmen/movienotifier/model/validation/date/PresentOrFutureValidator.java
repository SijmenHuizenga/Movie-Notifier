package it.sijmen.movienotifier.model.validation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class PresentOrFutureValidator implements ConstraintValidator<DateFuture, Date> {

    public final void initialize(final DateFuture annotation) {}

    public final boolean isValid(final Date value, final ConstraintValidatorContext context) {
        if(value == null)
            return false;

        Date now = new Date();

        return value.after(now) || value.equals(now);
    }
}
