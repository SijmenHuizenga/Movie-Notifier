package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.service.NotificationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("notificationtypes")
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    @Inject
    public NotificationTypeController(NotificationTypeService notificationTypeService) {
        this.notificationTypeService = notificationTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Notifier> getAll() {
        return notificationTypeService.getAllNotifiers();
    }

    @RequestMapping(value = "/{notificationtypekey}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Notifier getSingle(
            @PathVariable("notificationtypekey") String notificationtypekey
    ) {
        if(notificationtypekey == null || notificationtypekey.isEmpty())
            throw new BadRequestException("notificationtypekey must be provided.");
        return notificationTypeService.getNotifier(notificationtypekey);
    }

}
