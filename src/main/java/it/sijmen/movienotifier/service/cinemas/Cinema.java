package it.sijmen.movienotifier.service.cinemas;

import it.sijmen.movienotifier.model.Watcher;

import java.util.List;

public abstract class Cinema {

    public abstract String getCinemaIdPrefix();

    public abstract void checkWatcher(List<Watcher> watcher);

}
