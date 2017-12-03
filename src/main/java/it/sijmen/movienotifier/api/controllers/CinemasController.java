package it.sijmen.movienotifier.api.controllers;

import it.sijmen.movienotifier.service.cinemas.CinemaLocation;
import it.sijmen.movienotifier.service.cinemas.CinemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("cinemas")
public class CinemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CinemasController.class);

    private final CinemaService cinemaService;

    @Inject
    public CinemasController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CinemaLocation> getAll() {
        LOGGER.trace("Get all cinemas");
        return cinemaService.getAllCinemaLocations();
    }
}
