package it.sijmen.movienotifier.api;

import io.swagger.model.InlineResponse200;
import io.swagger.model.MonitorServiceStatus;
import io.swagger.model.WatcherServiceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("Status")
public class StatusApi {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void status(){

    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public ResponseEntity<MonitorServiceStatus> statusMonitorGet(){
        return null;
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public ResponseEntity<List<InlineResponse200>> statusNotificationsGet(){
        return null;
    }

    @RequestMapping(value = "/watchers", method = RequestMethod.GET)
    public ResponseEntity<WatcherServiceStatus> statusWatchersGet(){
        return null;
    }
}
