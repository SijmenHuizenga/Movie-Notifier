package it.sijmen.movienotifier.coder;

import java.io.IOException;

/**
 * Created by Sijmen on 15-4-2017.
 */
public interface Coder {

    <T> T decode(String json, Class<? extends T> dataClass) throws IOException;

    String encode(Object o) throws IOException;

}
