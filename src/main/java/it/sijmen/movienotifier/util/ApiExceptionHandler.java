package it.sijmen.movienotifier.util;

import it.sijmen.movienotifier.model.exceptions.ApiException;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

  private static final String BADREQUEST = "Bad Request";
  private static final String INTERNALSERVERERROR = "Internal Server Error";
  private static final String UNAUTH = "Unauthorized Request";

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public BadRequestException handleBadRequest(BadRequestException e) {
    LOGGER.debug(BADREQUEST, e);
    return e;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ServletRequestBindingException.class)
  @ResponseBody
  public BadRequestException handleBadRequest(ServletRequestBindingException e) {
    LOGGER.debug(BADREQUEST, e);
    return new BadRequestException(e.getMessage());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseBody
  public ApiException handleUnauthorizedException(UnauthorizedException e) {
    LOGGER.debug(UNAUTH, e);
    return new ApiException(UNAUTH);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ApiException handleInternalServerError(Exception e) {
    LOGGER.debug(INTERNALSERVERERROR, e);
    return new ApiException(INTERNALSERVERERROR + e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseBody
  public ApiException handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    LOGGER.debug(BADREQUEST, e);
    String s = e.getMostSpecificCause().getMessage();
    if (s.contains(":")) s = s.split(":")[0];
    else s = s.split("\n")[0];

    return new ApiException("JSON not valid: " + s);
  }
}
