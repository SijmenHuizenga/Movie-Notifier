package it.sijmen.movienotifier.api;

import io.swagger.api.StatusApi;
import io.swagger.model.InlineResponse200;
import io.swagger.model.MonitorServiceStatus;
import io.swagger.model.WatcherServiceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StatusApiController implements StatusApi {
    @Override
    public ResponseEntity<Void> statusGet() {
        return null;
    }

    @Override
    public ResponseEntity<MonitorServiceStatus> statusMonitorGet() {
        return null;
    }

    @Override
    public ResponseEntity<List<InlineResponse200>> statusNotificationsGet() {
        return null;
    }

    @Override
    public ResponseEntity<WatcherServiceStatus> statusWatchersGet() {
        return null;
    }
}
