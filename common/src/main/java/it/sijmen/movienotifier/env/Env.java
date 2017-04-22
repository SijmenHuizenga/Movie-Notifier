package it.sijmen.movienotifier.env;

import com.google.inject.ImplementedBy;

/**
 * Created by Sijmen on 22-4-2017.
 */
@ImplementedBy(RealEnv.class)
public interface Env {

    String getenv(String key);

}
