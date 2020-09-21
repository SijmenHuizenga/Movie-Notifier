package it.sijmen.movienotifier.model;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.util.ExceptionStringifier;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public interface Model {

  /** Validates the model. When something is wrong a error message is returned. Else null. */
  default void validate() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Model>> result = validator.validate(this);
    if (!result.isEmpty())
      throw new BadRequestException(new ExceptionStringifier().makeNiceArray(result));
  }
}
