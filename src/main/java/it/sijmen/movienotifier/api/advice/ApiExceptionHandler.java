package it.sijmen.movienotifier.api.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.movienotifier.model.exceptions.ApiException;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.util.Collections;

@ControllerAdvice
public class ApiExceptionHandler {

    private final ObjectMapper mapper;

    @Inject
    public ApiExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public String handleBadRequest(BadRequestException e) {
        try {
            return mapper.writeValueAsString(e);
        } catch (JsonProcessingException e1) {
            return e.getMessage();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public String handleBadRequest(ServletRequestBindingException e) {
        try {
            return mapper.writeValueAsString(
                    new BadRequestException(Collections.singletonList(e.getMessage()))
            );
        } catch (JsonProcessingException e1) {
            return e.getMessage();
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String handleUnauthorizedException(UnauthorizedException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleInternalServerError(Exception e) {
        try {
            return mapper.writeValueAsString(
                    new ApiException("Internal server error: " + e.getMessage())
            );
        } catch (JsonProcessingException e1) {
            return e.getMessage();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        try {
            String s = e.getMostSpecificCause().getMessage();
            if(s.contains(":"))
                s = s.split(":")[0];
            else
                s = s.split("\n")[0];

            return mapper.writeValueAsString(
                    new ApiException("JSON not valid: " + s)
            );
        } catch (JsonProcessingException e1) {
            return e.getMessage();
        }
    }

}
