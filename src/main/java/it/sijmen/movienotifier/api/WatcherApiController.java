package it.sijmen.movienotifier.api;

import io.swagger.model.Watcher;
import io.swagger.model.WatcherProps;
import it.sijmen.movienotifier.model.WatcherDetails;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.service.AuthenticationService;
import it.sijmen.movienotifier.service.WatcherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/watchers")
public class WatcherApiController {

    private final WatcherService watcherService;

    @Inject
    public WatcherApiController(WatcherService watcherService) {
        this.watcherService = watcherService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Watcher create(
            @RequestHeader("APIKEY") String apikey,
            @RequestBody Watcher watcher) {
        if (apikey == null)
            throw new BadRequestException("apikey is not provided");
        return watcherService.createWatcher(apikey, apiToModel(watcher)).toSwaggerWatcher();
    }

    private it.sijmen.movienotifier.model.Watcher apiToModel(Watcher watcher) {

        return new it.sijmen.movienotifier.model.Watcher(
                null, watcher.getUser(), watcher.getName(),
                watcher.getMovieid(), watcher.getCinemaid(),
                watcher.getStartAfter() == null ? null : watcher.getStartAfter().toDate(),
                watcher.getStartBefore() == null ? null : watcher.getStartBefore().toDate(),
                apiToModel(watcher.getProps())
        );
    }

    private WatcherDetails apiToModel(WatcherProps props) {
        if(props == null)
            return null;
        return new WatcherDetails(
                props.getOv(),
                props.getNl(),
                props.getImax(),
                props.get3d(),
                props.getHfr(),
                props.get4k(),
                props.getLaser(),
                props.getDbox(),
                props.getDolbycinema(),
                props.getDolbyatmos()
        );
    }
}
