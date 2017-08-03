package it.sijmen.movienotifier.model.validation.date;

import org.joda.time.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class PresentOrFutureValidator implements ConstraintValidator<DateFuture, Long> {

    public final void initialize(final DateFuture annotation) {}

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value > new DateTime().getMillis();

    }
}
