package it.sijmen.movienotifier.coder;

import com.google.inject.ImplementedBy;

import java.io.IOException;

/**
 * Created by Sijmen on 15-4-2017.
 */
@ImplementedBy(JsonCoder.class)
public interface Coder {

    <T> T decode(String json, Class<? extends T> dataClass) throws IOException;

    <T> T decode(String json, T obj) throws IOException;

    String encode(Object o) throws IOException;

}
