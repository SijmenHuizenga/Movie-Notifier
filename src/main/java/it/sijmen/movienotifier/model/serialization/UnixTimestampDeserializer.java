package it.sijmen.movienotifier.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class UnixTimestampDeserializer extends JsonDeserializer<Long> {

    public static final SimpleDateFormat PATHEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return PATHEFORMAT.parse(p.getText().trim()).getTime();
        } catch (Exception e) {
            return -1L;
        }
    }
}
