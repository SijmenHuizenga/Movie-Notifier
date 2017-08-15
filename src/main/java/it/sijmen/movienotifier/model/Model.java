package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.util.ExceptionStringifier;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public interface Model {

    /**
     * Validates the user. When something is wrong a error message is returned.
     * Else null.
     */
    default void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> result = validator.validate(this);
        if(!result.isEmpty())
            throw new BadRequestException(new ExceptionStringifier().makeNiceArray(result));
    }

}
