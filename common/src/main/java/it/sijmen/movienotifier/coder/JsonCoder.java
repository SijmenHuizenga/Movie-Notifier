package it.sijmen.movienotifier.coder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

import java.io.IOException;

/**
 * Created by Sijmen on 15-4-2017.
 */
public class JsonCoder implements Coder {

    protected ObjectMapper mapper;

    public JsonCoder() {
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public <T> T decode(String json, Class<? extends T> dataClass) throws IOException {
        return mapper.readValue(json, dataClass);
    }

    @Override
    public String encode(Object o) throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    protected ResponseTransformer getJsonTransformer() {
        return model -> mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
    }
}
