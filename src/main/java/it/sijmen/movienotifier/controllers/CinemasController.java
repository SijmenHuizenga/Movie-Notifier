package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.Cinema;
import it.sijmen.movienotifier.service.pathe.CinemaService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("cinemas")
public class CinemasController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CinemasController.class);

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Cinema> getAll() {
    LOGGER.trace("Get all pathe cinemas");
    return CinemaService.getAllCinemaLocations();
  }
}
