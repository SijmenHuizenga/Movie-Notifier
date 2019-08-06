package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String error() {
    throw new BadRequestException("Generic bad request exception");
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
