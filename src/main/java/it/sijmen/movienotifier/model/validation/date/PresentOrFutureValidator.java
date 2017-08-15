package it.sijmen.movienotifier.model.validation.date;

import org.joda.time.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PresentOrFutureValidator implements ConstraintValidator<DateFuture, Long> {

    public final void initialize(final DateFuture annotation) {
        //nothing to do here
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value > new DateTime().getMillis();

    }
}
