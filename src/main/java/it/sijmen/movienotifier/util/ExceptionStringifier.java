package it.sijmen.movienotifier.util;

import it.sijmen.movienotifier.model.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class ExceptionStringifier {

  public List<String> makeNiceArray(Set<ConstraintViolation<Model>> result) {
    List<String> out = new ArrayList<>();
    for (ConstraintViolation v : result) out.add(v.getPropertyPath() + " " + v.getMessage());
    return out;
  }
}
