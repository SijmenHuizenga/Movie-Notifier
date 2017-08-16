package it.sijmen.movienotifier.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixTimestampDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String datetime = p.getText().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date parsedDate = dateFormat.parse(datetime);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp.getTime();
        } catch (Exception e) {
            return -1L;
        }
    }
}
