package it.sijmen.movienotifier.util;

import it.sijmen.movienotifier.model.User;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExceptionStringifier {

    public static String exceptionToString(Throwable e){
        if(e == null)
            throw new IllegalArgumentException("Null throwable");
        if(e instanceof SQLIntegrityConstraintViolationException)
            return e.getMessage().replaceFirst("for key .+", "");
        if(e instanceof ConstraintViolationException){
            return " - " + ((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map(i -> i.getPropertyPath() + " " + i.getMessage())
                    .collect(Collectors.joining(", \n - "));
        }
        return e.getMessage();
    }

    public static String makeNice(Set<ConstraintViolation<User>> result) {
        return String.join("\n", makeNiceArray(result));
    }

    public static List<String> makeNiceArray(Set<ConstraintViolation<User>> result) {
        List<String> out = new ArrayList<>();
        for(ConstraintViolation<User> v : result)
            out.add(v.getPropertyPath() + " " + v.getMessage());
        return out;
    }
}
