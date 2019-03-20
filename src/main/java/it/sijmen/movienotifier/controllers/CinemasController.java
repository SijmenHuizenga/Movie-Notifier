package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.service.pathe.CinemaService;
import it.sijmen.movienotifier.model.Cinema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("cinemas")
public class CinemasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CinemasController.class);

    private final CinemaService cinemaService;

    @Autowired
    public CinemasController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Cinema> getAll() {
        LOGGER.trace("Get all pathe");
        return cinemaService.getAllCinemaLocations();
    }
}
