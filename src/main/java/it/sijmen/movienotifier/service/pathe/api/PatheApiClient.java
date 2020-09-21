package it.sijmen.movienotifier.service.pathe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PatheApiClient {

  private ObjectMapper mapper;
  private String patheApiKey;

  @Autowired
  public PatheApiClient(ObjectMapper mapper, @Value("${cinema.pathe.apikey}") String patheApiKey) {
    this.mapper = mapper;
    this.patheApiKey = patheApiKey;
  }

  public PatheShowings getShowingsForMovie(int movieId) throws IOException {
    String uri = "https://connect.pathe.nl/v1/movies/" + movieId + "/schedules";
    HttpResponse<String> stringHttpResponse = makeGetRequest(uri);

    if (stringHttpResponse.getStatus() != 200)
      throw new IOException(
          "Status returned " + stringHttpResponse.getStatus() + " after request " + uri);
    PatheShowings patheShowings =
        mapper.readValue(stringHttpResponse.getBody(), PatheShowings.class);
    if (patheShowings == null) throw new IOException("Unexpected api result");
    patheShowings.setMovieid(movieId);
    return patheShowings;
  }

  HttpResponse<String> makeGetRequest(String uri) throws IOException {
    try {
      return Unirest.get(uri).header("X-Client-Token", patheApiKey).asString();
    } catch (UnirestException e) {
      throw new IOException("Could not load request.", e);
    }
  }
}
