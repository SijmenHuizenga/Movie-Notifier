package it.sijmen.movienotifier.pathe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import it.sijmen.movienotifier.pathe.dto.MovieSchedulePerCinema;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by Sijmen on 7-4-2017.
 */
@Singleton
public class PatheApi {

    public static final String API_HEADER_ACCEPT_LANGUAGE = "nl-NL";
    public static final String API_HEADER_SITE_ID = "2";
    public static final String API_HEADER_X_OPERATING_SYSTEM_VALUE = "Android";
    public static final String NFC_MIME_TYPE = "application/json+pathe-ticket";

    @Inject
    @Named("pathe-api-key")
    private String patheApiKey;

    @Inject
    private ObjectMapper mapper;

    public PatheApi() {
    }

    private String send(String uri) throws IOException {
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = Unirest.get(PatheUrl.API_URL + uri)
                    .header("X-Client-Token", patheApiKey)
                    .asString();
        } catch (UnirestException e) {
            throw new IOException("Could not load request.", e);
        }
        if(stringHttpResponse.getStatus() != 200)
            throw new IOException("Status returned " + stringHttpResponse.getStatus());
        return stringHttpResponse.getBody();
    }

    public MovieSchedulePerCinema getMovieSchedulePerCinema(long movieId) throws IOException {
        String requestURL = String.format(PatheUrl.MOVIE_PER_CINEMA_SCHEDULES, movieId);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(send(requestURL), MovieSchedulePerCinema.class);
    }

    public <T> T parse(String text, Class<T> tClass) throws IOException {
        return mapper.readValue(text, tClass);
    }

    public String encode(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }
}
