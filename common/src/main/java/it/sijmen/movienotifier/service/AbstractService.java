package it.sijmen.movienotifier.service;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import it.sijmen.movienotifier.coder.Coder;
import spark.Spark;

import java.io.IOException;

/**
 * Created by Sijmen on 13-4-2017.
 */
public abstract class AbstractService {

    private Coder coder;
    protected final String[] requiredEnvironmentVariables;

    public AbstractService(Coder coder, String... requiredEnvironmentVariables) {
        this.coder = coder;
        this.requiredEnvironmentVariables = requiredEnvironmentVariables;
        checkRequirements();

        Spark.port(80);
    }

    private void checkRequirements() {
        for(String envKey : this.requiredEnvironmentVariables)
            if(System.getenv(envKey) == null)
                throw new IllegalStateException("Missing environment variable " + envKey);
    }

    protected <T> T sendGet(Class<? extends T> responseClass, String url, HttpMethod method) throws IOException {
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = new GetRequest(method, url).asString();
        } catch (UnirestException e) {
            throw new IOException("Could not load request.", e);
        }
        if(stringHttpResponse.getStatus() != 200)
            throw new IOException("Status returned " + stringHttpResponse.getStatus() + " after request " + url);

        return coder.decode(stringHttpResponse.getBody(), responseClass);
    }

    protected <T> T sendPost(Object object, Class<? extends T> responseClass, String url, HttpMethod method) throws IOException {
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = new HttpRequestWithBody(method, url).body(coder.encode(object)).asString();
        } catch (UnirestException e) {
            throw new IOException("Could not load request.", e);
        }
        if(stringHttpResponse.getStatus() != 200)
            throw new IOException("Status returned " + stringHttpResponse.getStatus() + " after request " + url);

        return coder.decode(stringHttpResponse.getBody(), responseClass);
    }

}
