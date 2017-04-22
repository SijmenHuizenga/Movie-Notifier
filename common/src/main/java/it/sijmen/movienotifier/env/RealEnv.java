package it.sijmen.movienotifier.env;

/**
 * Created by Sijmen on 22-4-2017.
 */
public class RealEnv implements Env {
    @Override
    public String getenv(String key) {
        return System.getenv(key);
    }
}
