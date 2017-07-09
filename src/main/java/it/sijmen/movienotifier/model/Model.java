package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.util.ExceptionStringifier;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class Model {

    /**
     * Validates the user. When something is wrong a error message is returned.
     * Else null.
     */
    public void validate() throws IllegalArgumentException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> result = validator.validate(this);
        if(result.size() != 0)
            throw new BadRequestException(ExceptionStringifier.makeNiceArray(result));
    }

}
