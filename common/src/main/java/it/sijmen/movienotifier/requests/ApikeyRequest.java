package it.sijmen.movienotifier.requests;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sijmen on 15-4-2017.
 */
public abstract class ApikeyRequest extends Request {

    private List<String> keys;

    public ApikeyRequest(String... apiKeys) {
        this.keys = Arrays.asList(apiKeys);
    }

    public boolean authorized() {
        return keys.contains(getApikey());
    }

    public abstract String getApikey();
}
