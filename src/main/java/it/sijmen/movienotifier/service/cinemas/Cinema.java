package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;

import java.util.List;

public interface Cinema {

    String getCinemaIdPrefix();

    void checkWatcher(List<Watcher> watcher);

}
