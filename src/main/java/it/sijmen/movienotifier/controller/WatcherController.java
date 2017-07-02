package it.sijmen.movienotifier.controller;

import io.swagger.api.WatchersApi;
import io.swagger.model.Watcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WatcherController implements WatchersApi {
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
    public ResponseEntity<List<Watcher>> watchersUserUseridGet(UUID userid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersUserUseridPut(UUID userid, Watcher watcher) {
        return null;
    }

    @Override
    public ResponseEntity<Void> watchersWatcheridDelete(UUID watcherid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersWatcheridGet(UUID watcherid) {
        return null;
    }

    @Override
    public ResponseEntity<Watcher> watchersWatcheridPost(UUID watcherid, Watcher watcher) {
        return null;
    }
}
