package it.sijmen.movienotifier.pathe.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class PatheApi {

    public static final String API_HEADER_ACCEPT_LANGUAGE = "nl-NL";
    public static final String API_HEADER_SITE_ID = "2";
    public static final String API_HEADER_X_OPERATING_SYSTEM_VALUE = "Android";
    public static final String NFC_MIME_TYPE = "application/json+pathe-ticket";

    @Inject
    @Named("pathe-api-key")
    private String patheApiKey;

    public PatheApi() {
    }

    public String send(){
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = Unirest.post("http://httpbin.org/post")
                    .header("X-Client-Token", patheApiKey)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
        return "";
    }
}
