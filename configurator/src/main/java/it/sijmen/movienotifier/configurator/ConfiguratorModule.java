package it.sijmen.movienotifier.configurator;

import com.google.inject.AbstractModule;
import it.sijmen.movienotifier.coder.Coder;
import it.sijmen.movienotifier.coder.JsonCoder;

/**
 * Created by Sijmen on 17-4-2017.
 */
public class ConfiguratorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Coder.class).to(JsonCoder.class);
    }

}
