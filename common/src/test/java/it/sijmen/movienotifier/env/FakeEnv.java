package it.sijmen.movienotifier.env;

import java.util.HashMap;

/**
 * Created by Sijmen on 22-4-2017.
 */
public class FakeEnv implements Env {

    private final HashMap<String, String> env = new HashMap<>();

    @Override
    public String getenv(String key) {
        return env.get(key);
    }

    public void setenv(String key, String value){
        this.env.put(key, value);
    }
}
