package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("notificationtypes")
public class NotificationTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTypeController.class);

    private final NotificationService notificationTypeService;

    @Inject
    public NotificationTypeController(NotificationService notificationTypeService) {
        this.notificationTypeService = notificationTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Notifier> getAll() {
        LOGGER.trace("Get all notification types");
        return notificationTypeService.getAllNotifiers();
    }

    @RequestMapping(value = "/{notificationtypekey}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Notifier getSingle(@PathVariable("notificationtypekey") String notificationtypekey) {
        if(notificationtypekey == null || notificationtypekey.isEmpty()) {
            LOGGER.trace("Could not retreve notificationtype {}", notificationtypekey);
            throw new BadRequestException("notificationtypekey must be provided.");
        }
        LOGGER.trace("Get notification type {}", notificationtypekey);
        return notificationTypeService.getNotifier(notificationtypekey);
    }

}
