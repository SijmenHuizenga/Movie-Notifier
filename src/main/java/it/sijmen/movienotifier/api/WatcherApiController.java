package it.sijmen.movienotifier.api;

import io.swagger.api.WatchersApi;
import io.swagger.model.Watcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WatcherApiController implements WatchersApi {
    @Override
    public ResponseEntity<List<Watcher>> watchersGet() {
        return null;
    }

    @Override
    public ResponseEntity<List<Integer>> watchersMoviesGet() {
        return null;
    }

    @Override
    public ResponseEntity<List<Watcher>> watchersMoviesMovieidGet(Integer movieid) {
        return null;
    }

    @Override
    public ResponseEntity<List<Watcher>> watchersUserUseridGet(String userid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersUserUseridPut(String userid, Watcher watcher) {
        return null;
    }

    @Override
    public ResponseEntity<Void> watchersWatcheridDelete(String watcherid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersWatcheridGet(String watcherid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersWatcheridPost(String watcherid, Watcher watcher) {
        return null;
    }
}
